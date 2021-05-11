import java.util.ArrayList;

public class FinalStage extends Stage
{

    ArrayList<Item> completedItems;

    public FinalStage(StorageQueue<Item> prev_)
    {
        prev = prev_;
        status = "waiting";
        completedItems = new ArrayList<Item>();
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
        System.out.println("Starved");
        if (prev.size() > 0)
        {
            status = "waiting";
        }
    }

    @Override
    protected TimeEvent waiting(double currentTime, double processingTime)
    {

        if (prev.size() > 0)
        {
            status = "busy";
            item = prev.remove();
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

    public ArrayList<Item> report()
    {
        return completedItems;
    }
}
