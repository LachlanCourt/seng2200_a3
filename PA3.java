public class PA3
{
    private double M;
    private double N;
    private int Qmax;

    public static void main(String[] args)
    {
        if (args.length < 3 || args.length > 4)
        {
            System.err.println("Invalid input data. Terminating...");
            System.exit(-1);
        }
        PA3 assign = new PA3();
        assign.run(args);
    }

    public void run(String[] args)
    {
        // Set up values
        M = Double.valueOf(args[0]); // Average processing time
        N = Double.valueOf(args[1]); // Range of processing time
        Qmax = Integer.valueOf(args[2]); // Capacity of storage queues
        String filename = null;
        if(args.length == 4)
        {
            filename = args[3];
        }

        ProductionLine line = new ProductionLine(M, N, Qmax, filename);
        line.produce();
        System.out.println(line.report());
    }
}
