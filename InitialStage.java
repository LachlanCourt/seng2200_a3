import java.util.Random;

public class InitialStage extends Stage
{
    public InitialStage(StorageQueue<Item> next_, String id_, double M_, double N_, Random rd_)
    {
        next = next_;
        rd = rd_;
        id = id_;
        setProcessingParams(M_, N_);
        processingFactor = 1;
        currentStatus = Statuses.WAITING;
        oldTime = 0;
        timeStarved = 0;
        timeBlocked = 0;
    }

    public InitialStage(StorageQueue<Item> next_, String id_, double M_, double N_, Random rd_, double processingFactor_)
    {
        this(next_, id_, M_, N_, rd_);
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
        // Will never be starved
        return;
    }

    @Override
    protected TimeEvent waiting(double currentTime)
    {
        oldTime = currentTime;
        currentStatus = Statuses.BUSY;
        // Create an item
        item = new Item();
        // Set processing time
        double processingTime = getProcessingTime();
        ProcessEvent newProcessEvent = new ProcessEvent(currentTime, currentTime + processingTime, id);
        item.addProcess(newProcessEvent);
        // Wait for time to be up
        return new TimeEvent(newProcessEvent.getEndTime());
    }

    public boolean getModificationFlag()
    {
        return modificationFlag;
    }

    public void resetModificationFlag()
    {
        modificationFlag = false;
    }

}
