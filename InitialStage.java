import java.util.Deque;

public class InitialStage extends Stage
{
    public InitialStage(Deque<Item> next_)
    {
        next = next_;
        busy = false;
    }

    @Override
    public TimeEvent process(double currentTime, double processingTime)
    {
        if (busy)
        {
            if (currentTime == item.getLastProcessEndTime())
            {
                busy = false;
            }
            // Add to queue
        }
        if (!busy)
        {
            busy = true;
            // Create an item
            item = new Item();
            // Set processing time
            Process newProcess = new Process(currentTime, currentTime + processingTime);
            item.addProcess(newProcess);
            // Wait for time to be up
            return new TimeEvent(newProcess.getEndTime());
        }

        return null;
    }
}
