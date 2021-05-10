import java.util.Deque;

public class InitialStage extends Stage
{
    public InitialStage(double Qmax_, Deque<Item> next_)
    {
        Qmax = Qmax_;
        next = next_;
        status = "waiting";
    }

    @Override
    public TimeEvent process(double currentTime, double processingTime)
    {


        if (status.compareTo("busy") == 0)
        {
            if (currentTime == item.getLastProcessEndTime())
            {
                status = "waiting";
                System.out.println(Qmax);
                if (next.size() < Qmax)
                {
                    System.out.println("Adding");
                    // Add to queue
                    next.add(item);
                }
                else
                {
                    System.out.println("Blocking");
                    status = "blocked";
                }
            }
            // Intentionally no break here, go to waiting
        }
        if (status.compareTo("waiting") == 0)
        {
            status = "busy";
            // Create an item
            item = new Item();
            // Set processing time
            Process newProcess = new Process(currentTime, currentTime + processingTime);
            item.addProcess(newProcess);
            // Wait for time to be up
            return new TimeEvent(newProcess.getEndTime());
        }
        if (status.compareTo("blocked") == 0)
        {
            System.out.println("Blocked");
        }

        return null;
    }
}
