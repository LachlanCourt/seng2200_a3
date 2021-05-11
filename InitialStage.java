import java.util.Queue;
import java.util.Random;

public class InitialStage extends Stage
{
    public InitialStage(StorageQueue<Item> next_, double M_, double N_, Random rd_)
    {
        setProcessingParams(M_, N_);
        rd = rd_;
        next = next_;
        processingFactor = 1;
        status = "waiting";
    }

    public InitialStage(StorageQueue<Item> next_, double M_, double N_, Random rd_, double processingFactor_)
    {
        this(next_, M_, N_, rd_);
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
        Process newProcess = new Process(currentTime, currentTime + processingTime);
        item.addProcess(newProcess);
        // Wait for time to be up
        return new TimeEvent(newProcess.getEndTime());
    }


}
