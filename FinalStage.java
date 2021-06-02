/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is a child class of a Stage in the production line. It
 ****    extends and implements the capability of the last stage in the line
 *******************************************************************************/
import java.util.ArrayList;

public class FinalStage extends Stage
{
    // Save all of the completed items for reporting
    ArrayList<Item> completedItems;

    // Main Constructor
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

    // Alternative constructor with optional processingFactor. This will be multiplied by M and N in getProcessingTime()
    public FinalStage(StorageQueue<Item> prev_, String id_, double processingFactor_)
    {
        this(prev_, id_);
        this.processingFactor = processingFactor_;
    }

    /**
     * Checks if the current item has finished being produced
     * @param currentTime current simulation time
     */
    @Override
    protected void busy(double currentTime)
    {
        // Update the oldTime which is only used when blocked or starved
        oldTime = currentTime;
        /*
         If the current Item has finished being processed, add it to the completed items and assume that the stage will
         now be STARVED
         */
        if (currentTime == item.getLastProcessEndTime())
        {
            completedItems.add(item);
            currentStatus = Statuses.STARVED;
        }
    }

    @Override
    protected void blocked(double currentTime)
    {
        // Will never be blocked as it is the last stage
        return;
    }

    /**
     * Tries to take an item from the previous queue
     * @param currentTime current simulation time
     */
    @Override
    protected void starved(double currentTime)
    {
        // Update the time starved for the purposes of reporting at the end of the run
        if (currentTime != oldTime)
        {
            timeStarved += currentTime - oldTime;
            oldTime = currentTime;
        }
        // If there is an item in the previous queue, change status to WAITING to grab said Item
        if (prev.size() > 0)
        {
            currentStatus = Statuses.WAITING;
        }
    }

    /**
     * Grab an item from the previous queue and start processing
     * @param currentTime current simulation time
     * @return a TimeEvent for when the Stage is due to finish processing
     */
    @Override
    protected TimeEvent waiting(double currentTime)
    {
        // Update the oldTime which is only used when blocked or starved
        oldTime = currentTime;

        // If there is an Item in the previous queue, take the next one out
        if (prev.size() > 0)
        {
            // Update the status and remove the next Item from the Queue
            currentStatus = Statuses.BUSY;
            item = prev.remove(currentTime);
            // Because a Queue was changed, change the modification flag to true
            modificationFlag = true;
            // Calculate a random time the item is due to be complete
            double processingTime = getProcessingTime();
            /*
             Create a ProccessEvent to record the process of going through this particular Stage, and add that to the
             Item's record
             */
            ProcessEvent newProcessEvent = new ProcessEvent(currentTime, currentTime + processingTime, id);
            item.addProcess(newProcessEvent);

            // Wait for time to be up
            return new TimeEvent(newProcessEvent.getEndTime());
        }
        else
        {
            // If the previous queue is empty, change status to STARVED
            currentStatus = Statuses.STARVED;
            return null;
        }
    }

    // Getter used to generate the final report
    public ArrayList<Item> report()
    {
        return completedItems;
    }
}
