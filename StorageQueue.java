/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class represents an Queue that transfers Items between Stages. It
 ****    is a wrapper for a standard library Queue with some functionality to
 ****    gather data for the final report
 *******************************************************************************/
import java.util.LinkedList;
import java.util.Queue;

public class StorageQueue<T extends Item>
{
    // Instance variables
    private final int Qmax; // Max items in queue at any one time
    private final String id; // Name of Queue for final report
    private Queue<T> items; // Current Items in Queue

    // Used to calculate the Average Items in Queue for the final report
    private double itemsInQueueRecord;
    private double oldTime;

    // Used to calculate the Average Time in Queue for the final report
    private int thruput;
    private double timeInQueueRecord;

    // Main Constructor
    public StorageQueue(int Qmax_, String id_)
    {
        Qmax = Qmax_;
        id = id_;
        items = new LinkedList<>();
        oldTime = 0;
        itemsInQueueRecord = 0;
        thruput = 0;
        timeInQueueRecord = 0;
    }

    /**
     * Removes an item from the queue
     *
     * @param currentTime the current simulation time at the time of this method being run
     * @return the next Item in the queue
     * Precondition: Queue cannot be empty (Will return null otherwise)
     * Postcondition: reporting data is updated and return value
     */
    public Item remove(double currentTime)
    {
        // Record for average items in queue
        double timeDifference = currentTime - oldTime;
        oldTime = currentTime;
        itemsInQueueRecord += timeDifference * size();

        // Remove item from Queue and save as a temp
        Item temp = items.remove();

        // Record for average time in queue and then return
        thruput++;
        timeInQueueRecord += currentTime - temp.getLastProcessEndTime();

        return temp;
    }

    /**
     * Tries to add an item to the queue
     *
     * @param item_ Item to be added to the Queue
     * @return a boolean if the add was successful. False if Queue is full
     * Precondition: None
     * Postcondition: Item is added to Queue or return false
     */
    public boolean add(T item_)
    {
        // If there is space to add the item to the Queue
        if (items.size() < Qmax)
        {
            // Record item count
            double currentTime = item_.getLastProcessEndTime();
            double timeDifference = currentTime - oldTime;
            oldTime = currentTime;
            itemsInQueueRecord += timeDifference * size();

            // Add the item and return
            items.add(item_);
            return true;
        }
        // If there was no space, return false
        return false;
    }

    //----- Getters -----//
    public int size()
    {
        return items.size();
    }

    public double getAvgItems(double maxTime)
    {
        return itemsInQueueRecord / maxTime;
    }

    public double getAvgTime()
    {
        return timeInQueueRecord / thruput;
    }

    public String getID()
    {
        return id;
    }

}
