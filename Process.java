public class Process
{
    double startTime;
    double endTime;

    public Process()
    {

    }

    public double getProcessLength()
    {
        return endTime-startTime;
    }
}
