public class ProcessEvent
{
    private double startTime;
    private double endTime;
    private String stageID;

    public ProcessEvent(double startTime_, double endTime_, String stageID_)
    {
        startTime = startTime_;
        endTime = endTime_;
        stageID = stageID_;
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

    public String getStageID()
    {
        return stageID;
    }
}
