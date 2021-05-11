public class MidStage extends Stage
{

    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_, String id_)
    {
        prev = prev_;
        next = next_;
        id = id_;
        processingFactor = 1;
        status = "waiting";
    }

    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_, String id_, double processingFactor_)
    {
        this(prev_, next_, id_);
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
                status = "blocked";
            }
            else
            {
                modificationFlag = true;
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
            item = prev.remove(currentTime);
            modificationFlag = true;
            double processingTime = getProcessingTime();
            ProcessEvent newProcessEvent = new ProcessEvent(currentTime, currentTime + processingTime, id);
            item.addProcess(newProcessEvent);

            // Wait for time to be up
            return new TimeEvent(newProcessEvent.getEndTime());
        }
        else
        {
            status = "starved";
            return null;
        }
    }


}
