import java.util.Random;

public class PA3
{
    private double M;
    private double N;
    private double Qmax;


    private Random rd = new Random(24601);

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.err.println("Invalid input data. Terminating...");
            return;
        }
        PA3 assign = new PA3();
        assign.run(args);
    }

    public void run(String[] args)
    {
        // Set up values
        M = Double.valueOf(args[0]); // Average processing time
        N = Double.valueOf(args[1]); // Range of processing time
        Qmax = Double.valueOf(args[2]); // Capacity of storage
        for (int i = 0; i < 20; i++)
        {
            System.out.println(getProcessingTime());
        }
    }

    public double getProcessingTime()
    {
        return M + N * (rd.nextDouble() - 0.5);
    }
}
