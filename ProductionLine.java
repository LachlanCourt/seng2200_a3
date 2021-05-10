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

    private Stage S0;
    private Stage S1;
    private Stage S2A;
    private Stage S2B;
    private Stage S3;
    private Stage S4A;
    private Stage S4B;
    private Stage S5;

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

        Q01 = new LinkedList<>();
        completionTimes = new PriorityQueue<TimeEvent>();
        currentTime = 0;
    }

    public void produce()
    {
        while (currentTime < MAX_TIME)
        {
            TimeEvent temp;
            for (int i = 0; i < 40; i++)
            {
                temp = new TimeEvent(getProcessingTime());
                completionTimes.add(temp);
            }
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
