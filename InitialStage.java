import java.util.Queue;

public class InitialStage extends Stage {
    public InitialStage(StorageQueue<Item> next_) {
        next = next_;
        status = "waiting";
    }

    @Override
    protected void busy(double currentTime) {
        if (currentTime == item.getLastProcessEndTime()) {
            status = "waiting";
            if (!next.add(item)){
                System.out.println("Blocking");
                status = "blocked";
            }
        }
    }

    @Override
    protected TimeEvent waiting(double currentTime, double processingTime) {
        status = "busy";
        // Create an item
        item = new Item();
        // Set processing time
        Process newProcess = new Process(currentTime, currentTime + processingTime);
        item.addProcess(newProcess);
        // Wait for time to be up
        return new TimeEvent(newProcess.getEndTime());
    }

    @Override
    protected void blocked() {
        System.out.println("Blocked");
    }

    @Override
    protected void starved() {

    }
}
