public class Process
{
    double startTime;
    double endTime;

    public Process(double startTime_, double endTime_)
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
}
