import java.util.ArrayList;

public class FinalStage extends Stage
{

    ArrayList<Item> completedItems;

    public FinalStage(StorageQueue<Item> prev_, String id_)
    {
        prev = prev_;
        id = id_;
        processingFactor = 1;
        currentStatus = Statuses.WAITING;
        oldTime = 0;
        timeStarved = 0;
        timeBlocked = 0;
        completedItems = new ArrayList<Item>();
    }

    public FinalStage(StorageQueue<Item> prev_, String id_, double processingFactor_)
    {
        this(prev_, id_);
        this.processingFactor = processingFactor_;
    }

    @Override
    protected void busy(double currentTime)
    {
        oldTime = currentTime;
        if (currentTime == item.getLastProcessEndTime())
        {
            completedItems.add(item);
            currentStatus = Statuses.STARVED;
        }
    }

    @Override
    protected void blocked(double currentTime)
    {
        // Will never be blocked
        return;
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

    public ArrayList<Item> report()
    {
        return completedItems;
    }
}
