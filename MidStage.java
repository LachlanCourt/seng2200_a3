public class MidStage extends Stage
{
    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_, String id_)
    {
        prev = prev_;
        next = next_;
        id = id_;
        processingFactor = 1;
        currentStatus = Statuses.STARVED;
        oldTime = 0;
        timeStarved = 0;
        timeBlocked = 0;
    }

    public MidStage(StorageQueue<Item> prev_, StorageQueue<Item> next_, String id_, double processingFactor_)
    {
        this(prev_, next_, id_);
        this.processingFactor = processingFactor_;
    }

    @Override
    protected void busy(double currentTime)
    {
        oldTime = currentTime;
        if (currentTime == item.getLastProcessEndTime())
        {
            currentStatus = Statuses.WAITING;
            if (!next.add(item))
            {
                currentStatus = Statuses.BLOCKED;
            }
            else
            {
                modificationFlag = true;
            }
        }
    }

    @Override
    protected void blocked(double currentTime)
    {
        if (currentTime != oldTime)
        {
            timeBlocked += currentTime - oldTime;
            oldTime = currentTime;
        }
        if (next.add(item))
        {
            currentStatus = Statuses.WAITING;
        }
    }

    @Override
    protected void starved(double currentTime)
    {
        if (currentTime != oldTime)
        {
            timeStarved += currentTime - oldTime;
            oldTime = currentTime;
        }
        if (prev.size() > 0)
        {
            currentStatus = Statuses.WAITING;
        }
    }

    @Override
    protected TimeEvent waiting(double currentTime)
    {
        oldTime = currentTime;
        if (prev.size() > 0)
        {
            currentStatus = Statuses.BUSY;
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
            currentStatus = Statuses.STARVED;
            return null;
        }
    }
}
