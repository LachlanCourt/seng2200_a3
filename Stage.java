import java.util.Random;

public abstract class Stage
{
    protected static boolean modificationFlag = false;
    private static double M;
    private static double N;
    protected static Random rd;

    protected double processingFactor;

    protected String status = "waiting";
    protected Item item;

    protected StorageQueue<Item> prev;
    protected StorageQueue<Item> next;
    protected String id;

    protected double oldTime;
    protected double timeStarved;
    protected double timeBlocked;

    public TimeEvent process(double currentTime)
    {
        TimeEvent temp = null;
        if (status.compareTo("busy") == 0)
        {
            busy(currentTime);
        }
        if (status.compareTo("blocked") == 0)
        {
            blocked(currentTime);
        }
        if (status.compareTo("starved") == 0)
        {
            starved(currentTime);
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
        return (processingFactor * M) + (processingFactor * N) * (rd.nextDouble() - 0.5);
    }

    public double getTimeStarved()
    {
        return timeStarved;
    }

    public double getTimeBlocked()
    {
        return timeBlocked;
    }

    public String getId()
    {
        return id;
    }

    protected abstract void busy(double currentTime);

    protected abstract TimeEvent waiting(double currentTime);

    protected abstract void blocked(double currentTime);

    protected abstract void starved(double currentTime);

}
