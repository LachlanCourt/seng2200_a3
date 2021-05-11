public class MidStage extends Stage
{

    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_)
    {
        prev = prev_;
        next = next_;
        processingFactor = 1;
        status = "waiting";
    }

    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_, double processingFactor_)
    {
        this(prev_, next_);
        this.processingFactor = processingFactor_;
    }

    @Override
    protected void busy(double currentTime)
    {
        if (currentTime == item.getLastProcessEndTime())
        {
            status = "waiting";
            if (!next.add(item))
            {
                System.out.println("Blocking");
                status = "blocked";
            }
        }
    }

    @Override
    protected void blocked()
    {
        if (next.add(item))
        {
            status = "waiting";
        }
    }

    @Override
    protected void starved()
    {
        System.out.println("Starved");
        if (prev.size() > 0)
        {
            status = "waiting";
        }
    }

    @Override
    protected TimeEvent waiting(double currentTime)
    {
        if (prev.size() > 0)
        {
            status = "busy";
            item = prev.remove();
            double processingTime = getProcessingTime();
            Process newProcess = new Process(currentTime, currentTime + processingTime);
            item.addProcess(newProcess);
            // Wait for time to be up
            return new TimeEvent(newProcess.getEndTime());
        }
        else
        {
            status = "starved";
            return null;
        }
    }


}
