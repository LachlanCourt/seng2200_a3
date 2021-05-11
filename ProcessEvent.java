public class ProcessEvent
{
    double startTime;
    double endTime;

    public ProcessEvent(double startTime_, double endTime_)
    {
        startTime = startTime_;
        endTime = endTime_;

    }

    public double getProcessLength()
    {
        return endTime-startTime;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public double getStartTime()
    {
        return startTime;
    }
}
