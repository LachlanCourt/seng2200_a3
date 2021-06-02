/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is a child class of a Stage in the production line. It
 ****    extends and implements the capability of the first stage in the line
 *******************************************************************************/

import java.util.Random;

public class InitialStage extends Stage
{
    // Main constructor
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

    // Alternative constructor with optional processingFactor. This will be multiplied by M and N in getProcessingTime()
    public InitialStage(StorageQueue<Item> next_, String id_, double M_, double N_, Random rd_, double processingFactor_)
    {
        this(next_, id_, M_, N_, rd_);
        this.processingFactor = processingFactor_;
    }

    /**
     * Checks if the current item has finished being produced
     *
     * @param currentTime current simulation time
     */
    @Override
    protected void busy(double currentTime)
    {
        // Update the oldTime which is only used when blocked or starved
        oldTime = currentTime;
        //If the current Item has finished being processed, attempt to add it to the next queue
        if (currentTime == item.getLastProcessEndTime())
        {
            // Assume the add has been successful and set the status to WAITING
            currentStatus = Statuses.WAITING;
            if (!next.add(item)) // add() returns a boolean if successful
            {
                // If adding the item was unsuccessful, the next queue must be full.
                currentStatus = Statuses.BLOCKED;
            }
            else
            {
                // If adding the item was successful, update the modification flag
                modificationFlag = true;
            }
        }
    }

    /**
     * Tries to add an item to the next queue
     *
     * @param currentTime current simulation time
     */
    @Override
    protected void blocked(double currentTime)
    {
        // Update the time blocked for the purposes of reporting at the end of the run
        if (currentTime != oldTime)
        {
            timeBlocked += currentTime - oldTime;
            oldTime = currentTime;
        }
        if (next.add(item)) // add() returns a boolean if successful
        {
            // If the add was successful, update the status
            currentStatus = Statuses.WAITING;
        }
    }

    @Override
    protected void starved(double currentTime)
    {
        // Will never be starved as it is the initial stage
        return;
    }

    /**
     * Grab an item from the previous queue and start processing
     *
     * @param currentTime current simulation time
     * @return a TimeEvent for when the Stage is due to finish processing
     */
    @Override
    protected TimeEvent waiting(double currentTime)
    {
        // Update the oldTime which is only used when blocked or starved
        oldTime = currentTime;

        // Update the status and create an Item
        currentStatus = Statuses.BUSY;
        item = new Item();
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

    // Getter to determine if any of the Queues have been modified
    public boolean getModificationFlag()
    {
        return modificationFlag;
    }

    // Flag gets reset at the start of each loop in ProductionLine
    public void resetModificationFlag()
    {
        modificationFlag = false;
    }

}
