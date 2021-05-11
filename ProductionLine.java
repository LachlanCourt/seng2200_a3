import java.util.*;

public class ProductionLine
{
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

    public ProductionLine(double M_, double N_, int Qmax_)
    {
        Qmax = Qmax_;
        rd = new Random(100);

        initialiseLine(M_, N_);

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
                S0.resetModificationFlag();
                for (int i = 0; i < stages.size(); i++)
                {
                    temp = stages.get(i).process(currentTime);
                    if (temp != null)
                    {
                        completionTimes.add(temp);
                    }
                }
            }
            while (S0.getModificationFlag());

            //TEMPORARY --------------------------
            if (completionTimes.size() == 0)
            {
                System.err.println("Empty completionTimes");
                break;
            }
            //-------------------------------------

            // Now that all of the stages are actively processing, jump to the next time event
            currentTime = completionTimes.poll().getCompletionTime();
        }
    }

    public String report()
    {
        String working = "";
        working += "REPORT ON PRODUCTION\n\n";
        working += "Number of items produced: " + S5.report().size() + "\n\n";
        working += "Storage Queues:\n";
        for (int i = 0; i < queues.size(); i++)
        {
            working += queues.get(i).getID() + ":    " + "AvgItems: " + String.format("%5.4f", queues.get(i).getAvgItems(MAX_TIME)) + "    AvgTime: " + String.format("%5.4f", queues.get(i).getAvgTime()) + "\n";
        }
        ArrayList<Item> itemReport = S5.report();
        for (int i = 0; i < itemReport.size(); i++)
        {

        }

        // Output to text file here----

        //-----------------------------
        return working;
    }

    public void initialiseLine(double M, double N)
    {
        queues = new ArrayList<StorageQueue<Item>>();

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

        stages = new ArrayList<Stage>();

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
        S5 = new FinalStage(Q45, "S5");
        stages.add(S5);
    }
}
