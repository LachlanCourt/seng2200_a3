import java.util.ArrayList;

public class FinalStage extends Stage
{

    ArrayList<Item> completedItems;

    public FinalStage(StorageQueue<Item> prev_)
    {
        prev = prev_;
        processingFactor = 1;
        status = "waiting";
        completedItems = new ArrayList<Item>();
    }

    public FinalStage(StorageQueue<Item> prev_, double processingFactor_)
    {
        this(prev_);
        this.processingFactor = processingFactor_;
    }

    @Override
    protected void busy(double currentTime)
    {
        if (currentTime == item.getLastProcessEndTime())
        {
            completedItems.add(item);
            status = "waiting";
        }
    }

    @Override
    protected void blocked()
    {
        // Will never be blocked
        return;
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
            ProcessEvent newProcessEvent = new ProcessEvent(currentTime, currentTime + processingTime);
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

    public ArrayList<Item> report()
    {
        return completedItems;
    }
}
