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

        rd = new Random(24601);
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

            for (int i = 0; i < stages.size(); i++)
            {
            temp = stages.get(i).process(currentTime);
            if (temp != null)
            {
                completionTimes.add(temp);
                System.out.println(completionTimes.size());
            }}



            //TEMPORARY --------------------------
            if (completionTimes.size() == 0)
            {
                System.err.println("Empty completionTimes");
                break;
            }
            //-------------------------------------

            currentTime = completionTimes.poll().getCompletionTime();

//            if (completionTimes.isEmpty())
//            {
//                System.out.println("No more events!");
//                break;
//            }
        }
    }

    public String report()
    {
        String working = "";
        working += "Report on production";

        ArrayList<Item> temp = S5.report();
        int size = temp.size();
        for (int i = 0; i < size; i++)
        {
            working += temp.get(i) + "\n";
        }
        working += temp.size();
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
