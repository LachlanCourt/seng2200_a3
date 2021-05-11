import java.util.Random;

public abstract class Stage
{
    protected String status = "waiting";
    protected Item item;

    private static double M;
    private static double N;
    protected static Random rd;

    protected StorageQueue<Item> prev;
    protected StorageQueue<Item> next;

    public TimeEvent process(double currentTime)
    {
        TimeEvent temp = null;
        if (status.compareTo("busy") == 0)
        {
            busy(currentTime);
        }
        if (status.compareTo("blocked") == 0)
        {
            blocked();
        }
        if (status.compareTo("starved") == 0)
        {
            starved();
        }
        if (status.compareTo("waiting") == 0)
        {
            temp = waiting(currentTime);
        }
        return temp;
    }

    protected void setProcessingParams(double M_, double N_)
    {
        M = M_;
        N = N_;
    }

    protected double getProcessingTime()
    {
        return M + N * (rd.nextDouble() - 0.5);
    }

    protected abstract void busy(double currentTime);

    protected abstract TimeEvent waiting(double currentTime);

    protected abstract void blocked();

    protected abstract void starved();

}
