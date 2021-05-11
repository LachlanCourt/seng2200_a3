import java.sql.Time;
import java.util.*;

public class ProductionLine
{
    private StorageQueue<Item> Q01;
    private StorageQueue<Item> Q12;
    private StorageQueue<Item> Q23;
    private StorageQueue<Item> Q34;
    private StorageQueue<Item> Q45;

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

    private int Qmax;

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
        working += "Report on production\n";
        working += "Number of items produced: " + S5.report().size();
        working += "\nQueues:\n";
        for (int i = 0; i < queues.size(); i++)
        {
            working += "AvgItems: " + queues.get(i).getAvgItems(MAX_TIME) + "    AvgTime: " + queues.get(i).getAvgTime() + "\n";
        }
        return working;
    }

    public void initialiseLine(double M, double N)
    {
        queues = new ArrayList<StorageQueue<Item>>();

        Q01 = new StorageQueue<Item>(Qmax);
        queues.add(Q01);
        Q12 = new StorageQueue<Item>(Qmax);
        queues.add(Q12);
        Q23 = new StorageQueue<Item>(Qmax);
        queues.add(Q23);
        Q34 = new StorageQueue<Item>(Qmax);
        queues.add(Q34);
        Q45 = new StorageQueue<Item>(Qmax);
        queues.add(Q45);

        stages = new ArrayList<Stage>();

        S0 = new InitialStage(Q01, M, N, rd);
        stages.add(S0);
        S1 = new MidStage(Q01, Q12);
        stages.add(S1);
        S2A = new MidStage(Q12, Q23, 2);
        stages.add(S2A);
        S2B = new MidStage(Q12, Q23, 2);
        stages.add(S2B);

        S3 = new MidStage(Q23, Q34);
        stages.add(S3);
        S4A = new MidStage(Q34, Q45, 2);
        stages.add(S4A);
        S4B = new MidStage(Q34, Q45, 2);
        stages.add(S4B);
        S5 = new FinalStage(Q45);
        stages.add(S5);
    }
}
