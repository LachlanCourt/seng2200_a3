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
        status = "waiting";
    }

    public InitialStage(StorageQueue<Item> next_, String id_, double M_, double N_, Random rd_, double processingFactor_)
    {
        this(next_, id_, M_, N_, rd_);
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
        // Will never be starved
        return;
    }

    @Override
    protected TimeEvent waiting(double currentTime)
    {
        status = "busy";
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
