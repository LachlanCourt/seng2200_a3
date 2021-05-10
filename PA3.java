import java.util.Random;

public class PA3
{
    private double M;
    private double N;
    private double Qmax;

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

        ProductionLine line = new ProductionLine(M, N, Qmax);
        line.produce();
        System.out.println(line.report());

    }
}