public class ProcessEvent
{
    private double startTime;
    private double endTime;
    private String stageProcess;

    public ProcessEvent(double startTime_, double endTime_, String stageProcess_)
    {
        startTime = startTime_;
        endTime = endTime_;
        stageProcess = stageProcess_;
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

    public String getStageProcess()
    {
        return stageProcess;
    }
}
