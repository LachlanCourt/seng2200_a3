import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ProductionLine
{
    private InitialStage startStage;
    private FinalStage endStage;

    private InitialStage S0;
    private MidStage S1;
    private MidStage S2A;
    private MidStage S2B;
    private MidStage S3;
    private MidStage S4A;
    private MidStage S4B;
    private FinalStage S5;

    ArrayList<Stage> stages;
    ArrayList<StorageQueue<Item>> queues;

    private final int Qmax;

    PriorityQueue<TimeEvent> completionTimes;
    double currentTime;
    static final double MAX_TIME = 10000000;

    private Random rd;

    public ProductionLine(double M_, double N_, int Qmax_, String filename)
    {
        Qmax = Qmax_;
        rd = new Random(24601);

        if (!initialiseLineFromFile(M_, N_, filename))
        {
            initialiseDefaultLine(M_, N_);
        }

        completionTimes = new PriorityQueue<>();
        currentTime = 0;
    }

    public void produce()
    {
        TimeEvent temp;
        // Main run
        while (currentTime < MAX_TIME)
        {
            // Loop until all stages are effectively "Busy" - No modification is happening in the queues whether adding or removing
            do
            {
                startStage.resetModificationFlag();
                for (int i = 0; i < stages.size(); i++)
                {
                    temp = stages.get(i).process(currentTime);
                    if (temp != null)
                    {
                        completionTimes.add(temp);
                    }
                }
            }
            while (startStage.getModificationFlag());

            // Now that all of the stages are actively processing, jump to the next time event
            currentTime = completionTimes.remove().getCompletionTime();
        }
    }

    public String report()
    {
        String working = "";
        working += "PRODUCTION REPORT\n\n";

        ArrayList<Item> itemReport = endStage.report();

        working += "Number of items produced: " + itemReport.size() + "\n";
        working += "Time taken: " + (int) MAX_TIME + " units\n\n";

        // PRODUCTION STAGES -------------------------------------------------------------------------------------------
        working += "Production Stages:\n\n";
        for (int i = 0; i < stages.size(); i++)
        {
            String s = " ".repeat(3 - stages.get(i).getId().length());
            String s2 = " ".repeat(15 - String.format("%5.4f", stages.get(i).getTimeStarved()).length());
            double divisor = stages.get(i).getTimeStarved() + stages.get(i).getTimeBlocked();
            working += stages.get(i).getId() + ":" + s + "    Work: " + String.format("%2.4f", (100 - (divisor / MAX_TIME) * 100)) + "%    Starved: " + String.format("%5.4f", stages.get(i).getTimeStarved()) + s2 + "    Blocked: " + String.format("%5.4f", stages.get(i).getTimeBlocked()) + "\n";
            //working += "--------------------------------------------------------------------------\n";
        }

        // STORAGE QUEUES ---------------------------------------------------------------------------------------------
        working += "\nStorage Queues:\n\n";
        for (int i = 0; i < queues.size(); i++)
        {
            working += queues.get(i).getID() + ":    " + "AvgItems: " + String.format("%5.4f", queues.get(i).getAvgItems(MAX_TIME)) + "    AvgTime: " + String.format("%5.4f", queues.get(i).getAvgTime()) + "\n";
            //working += "-----------------------------------------------\n";
        }

        // PRODUCTION PATHS -------------------------------------------------------------------------------------------
        HashMap<String, Integer> pathData = new HashMap<>();
        for (int i = 0; i < itemReport.size(); i++)
        {
            Item currentItem = itemReport.get(i);
            ArrayList<ProcessEvent> processReport = currentItem.report();

            String pathCode = "";
            for (int j = 0; j < processReport.size(); j++)
            {
                pathCode += processReport.get(j).getStageID() + " -> ";
            }
            pathCode = pathCode.substring(0, pathCode.length() - 4);
            if (pathData.containsKey(pathCode))
            {
                pathData.put(pathCode, pathData.get(pathCode) + 1);
            }
            else
            {
                pathData.put(pathCode, 1);
            }
        }

        working += "\nProduction Paths:\n\n";
        for (String key : pathData.keySet())
        {
            working += key + ": " + pathData.get(key) + "\n";
            //working += "-----------------------------------------\n";
        }

        // Output to text file
        PrintWriter out;
        try
        {
            out = new PrintWriter("production_report.txt");
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e);
            return working;
        }
        out.println(working);
        out.close();

        return working + "\nProgram complete. Data outputted to \"production_report.txt\"";
    }

    public void initialiseDefaultLine(double M, double N)
    {
        System.out.println("Loading default production line...\n");

        queues = new ArrayList<>();

        StorageQueue<Item> Q01 = new StorageQueue<Item>(Qmax, "Q01");
        queues.add(Q01);
        StorageQueue<Item> Q12 = new StorageQueue<Item>(Qmax, "Q12");
        queues.add(Q12);
        StorageQueue<Item> Q23 = new StorageQueue<Item>(Qmax, "Q23");
        queues.add(Q23);
        StorageQueue<Item> Q34 = new StorageQueue<Item>(Qmax, "Q34");
        queues.add(Q34);
        StorageQueue<Item> Q45 = new StorageQueue<Item>(Qmax, "Q45");
        queues.add(Q45);

        stages = new ArrayList<>();

        S0 = new InitialStage(Q01, "S0", M, N, rd);
        stages.add(S0);
        S1 = new MidStage(Q01, Q12, "S1");
        stages.add(S1);

        S2A = new MidStage(Q12, Q23, "S2A", 2);
        stages.add(S2A);
        S2B = new MidStage(Q12, Q23, "S2B", 2);
        stages.add(S2B);

        S3 = new MidStage(Q23, Q34, "S3");
        stages.add(S3);

        S4A = new MidStage(Q34, Q45, "S4A", 2);
        stages.add(S4A);
        S4B = new MidStage(Q34, Q45, "S4B", 2);
        stages.add(S4B);

        FinalStage S5 = new FinalStage(Q45, "S5");
        stages.add(S5);

        startStage = S0;
        endStage = S5;
    }

    public boolean initialiseLineFromFile(double M, double N, String filename)
    {
        Scanner input;
        try
        {
            input = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            return false;
        }
        System.out.println("Interpretting production line from text file...\n");

        // Read Queues
        queues = new ArrayList<>();
        StorageQueue<Item> tempQueue;
        while (input.hasNext("Q.*"))
        {
            queues.add(new StorageQueue<Item>(Qmax, input.nextLine()));
        }

        // Read Stages
        stages = new ArrayList<>();

        // InitialStage
        if (input.hasNext("S.*"))
        {
            String id = input.next();
            String nextId = input.next();

            StorageQueue<Item> next = getQueueByID(nextId);
            if (next == null)
            {
                return false;
            }

            double processingFactor = 1;
            if (input.hasNextDouble())
            {
                processingFactor = input.nextDouble();
            }
            stages.add(new InitialStage(next, id, M, N, rd, processingFactor));
        }

        while (input.hasNext("S.*"))
        {
            String id = input.next();
            String prevId = input.next();
            String nextId = input.next();

            StorageQueue<Item> prev = getQueueByID(prevId);
            StorageQueue<Item> next = getQueueByID(nextId);
            if (next == null || prev == null)
            {
                return false;
            }

            double processingFactor = 1;
            if (input.hasNextDouble())
            {
                processingFactor = input.nextDouble();
            }
            stages.add(new MidStage(prev, next, id, processingFactor));
        }

        // Final stage
        if (input.next().compareTo("F") == 0)
        {
            //input.nextLine();
            String id = input.next();
            String prevId = input.next();

            StorageQueue<Item> prev = getQueueByID(prevId);
            if (prev == null)
            {
                return false;
            }

            double processingFactor = 1;
            if (input.hasNextDouble())
            {
                processingFactor = input.nextDouble();
            }
            stages.add(new FinalStage(prev, id, processingFactor));

            startStage = (InitialStage) stages.get(0);
            endStage = (FinalStage) stages.get(stages.size() - 1);
        }
        return true;
    }

    private StorageQueue<Item> getQueueByID(String nextID)
    {
        for (int i = 0; i < queues.size(); i++)
        {
            if (queues.get(i).getID().compareTo(nextID) == 0)
            {
                return queues.get(i);
            }
        }
        return null;
    }
}
