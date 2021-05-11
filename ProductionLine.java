import java.sql.Time;
import java.util.*;

public class ProductionLine
{
    private StorageQueue<Item> Q01;
    private Queue<Item> Q12;
    private Queue<Item> Q23;
    private Queue<Item> Q34;
    private Queue<Item> Q45;

    private InitialStage S0;
    private MidStage S1;
    private MidStage S2A;
    private MidStage S2B;
    private MidStage S3;
    private MidStage S4A;
    private MidStage S4B;
    private FinalStage S5;

    private double M;
    private double N;
    private int Qmax;

    PriorityQueue<TimeEvent> completionTimes;
    double currentTime;
    static final double MAX_TIME = 1000000;

    private Random rd = new Random(24601);

    public ProductionLine(double M_, double N_, int Qmax_)
    {
        M = M_;
        N = N_;
        Qmax = Qmax_;


        Q01 = new StorageQueue<Item>(Qmax);


        S0 = new InitialStage(Q01);


        completionTimes = new PriorityQueue<>();
        currentTime = 0;
    }

    public void produce()
    {

        // Main run
        while (currentTime < MAX_TIME)
        {
            TimeEvent temp = S0.process(currentTime, getProcessingTime());
            if (temp != null)
            {
                completionTimes.add(temp);
                System.out.println(completionTimes.size());
            }

            //TEMPORARY --------------------------
            if (completionTimes.size() == 0)
            {
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
        System.out.print("Size: ");
        System.out.println(Q01.size());
        int size = completionTimes.size();
        for (int i = 0; i < size; i++)
        {
            System.out.println(i);
            working += completionTimes.poll().getCompletionTime() + "\n";
        }
        return working;
    }

    private double getProcessingTime()
    {
        return M + N * (rd.nextDouble() - 0.5);
    }
}
