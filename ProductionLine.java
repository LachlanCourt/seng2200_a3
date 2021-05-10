import java.util.PriorityQueue;

public class ProductionLine
{
    private PriorityQueue<Item> Q01;
    private PriorityQueue<Item> Q12;
    private PriorityQueue<Item> Q23;
    private PriorityQueue<Item> Q34;
    private PriorityQueue<Item> Q45;

    private Stage S0;
    private Stage S1;
    private Stage S2A;
    private Stage S2B;
    private Stage S3;
    private Stage S4A;
    private Stage S4B;
    private Stage S5;

    public ProductionLine(double M, double N, double Qmax)
    {

    }

    public void run()
    {

    }

    public String report()
    {
        return "Report on production";
    }
}
