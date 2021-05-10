import java.sql.Time;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class ProductionLine
{
    private Deque<Item> Q01;
    private Deque<Item> Q12;
    private Deque<Item> Q23;
    private Deque<Item> Q34;
    private Deque<Item> Q45;

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
    private double Qmax;

    PriorityQueue<TimeEvent> completionTimes;
    double currentTime;
    double MAX_TIME = 1000000;

    private Random rd = new Random(24601);

    public ProductionLine(double M_, double N_, double Qmax_)
    {
        M = M_;
        N = N_;
        Qmax = Qmax_;

        S0 = new InitialStage(Q01);

        Q01 = new LinkedList<>();
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
