public class TimeEvent implements Comparable<TimeEvent>
{
    private double completionTime;

    public TimeEvent(double completionTime_)
    {
        completionTime = completionTime_;
    }

    public double getCompletionTime()
    {
        return completionTime;
    }

    @Override
    public int compareTo(TimeEvent o)
    {
        if (o.getCompletionTime() < this.completionTime)
        {
            return 1;
        }
        else if (o.getCompletionTime() == this.completionTime)
        {
            return 0;
        }
        return -1;
    }
}
