/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is a parent class of a Stage in the production line. It is
 ****    is abstract and declares the necessary functions to operate
 *******************************************************************************/
import java.util.Random;

public abstract class Stage
{
    enum Statuses
    {
        BUSY,
        BLOCKED,
        STARVED,
        WAITING
    }

    // Used to determine if a stage has added or removed to a queue
    protected static boolean modificationFlag = false;

    // These variables will be used to calculate the random processing time in a simulation
    private static double M;
    private static double N;
    protected static Random rd;
    protected double processingFactor;

    // Status of stage and the current Item being stored
    protected Statuses currentStatus = Statuses.WAITING;
    protected Item item;

    // The previous and next queues in the production line
    protected StorageQueue<Item> prev;
    protected StorageQueue<Item> next;

    // Reference ID of the stage
    protected String id;

    // Used to calculate the report at the end of a run
    protected double oldTime;
    protected double timeStarved;
    protected double timeBlocked;

    /**
     * The main call of the Stage class, indicating a step in the simulation
     *
     * @param currentTime the simulation time at the time the method is called
     * @return a TimeEvent object indicating the time the stage will be completed processing, or null if it is not
     * currently processing
     * Precondition: All variables should be initialised
     * Postcondition: A method will be called according to the current status, either busy, blocked, starved, or waiting
     */
    public TimeEvent process(double currentTime)
    {
        // Assume there is no TimeEvent to return
        TimeEvent temp = null;
        /*
         This attrocious stack of if statements is because it doesn't always only run one, and so it cannot be a switch
         case. The order is important as some of these methods will change the state from busy to blocked/starved or
         from blocked/starved to waiting.
         */
        if (currentStatus == Statuses.BUSY)
        {
            busy(currentTime);
        }
        if (currentStatus == Statuses.BLOCKED)
        {
            blocked(currentTime);
        }
        if (currentStatus == Statuses.STARVED)
        {
            starved(currentTime);
        }
        if (currentStatus == Statuses.WAITING)
        {
            temp = waiting(currentTime);
        }
        // If the WAITING method has been called, temp will be a TimeEvent. Otherwise it will still be null
        return temp;
    }

    /**
     * Set parameters for random processing
     * @param M_ Mean of random values
     * @param N_ Range of random values
     */
    protected void setProcessingParams(double M_, double N_)
    {
        M = M_;
        N = N_;
    }

    /**
     * Generate a random value based on a formula using M, N, and a processingFactor
     * @return a random value to be stored in a TimeEvent
     */
    protected double getProcessingTime()
    {
        return (processingFactor * M) + (processingFactor * N) * (rd.nextDouble() - 0.5);
    }

    //----- GETTERS -----//
    public double getTimeStarved()
    {
        return timeStarved;
    }
    public double getTimeBlocked()
    {
        return timeBlocked;
    }
    public String getId()
    {
        return id;
    }

    /**
     * This abstract method is when the item is currently being processed. It should check the currentTime it is passed
     * and attempt to pass it into the next queue when it is complete. If the next queue is full change status to BLOCKED
     * @param currentTime current simulation time
     * Precondition: waiting method should have been called
     * Postcondition: Depending on currentTime, Item may have been added to a queue, status changed to BLOCKED, or NOP
     */
    protected abstract void busy(double currentTime);

    /**
     * This abstract method is when an Item has just finished being processed. It should attempt to take an item from the
     * previous queue and change status to BUSY. If the previous queue is empty change status to STARVED
     * @param currentTime current simulation time
     * @return TimeEvent indicating the time the current Item will be finished production
     * Precondition: Stage should be initialised
     * Postcondition: An item is taken from the previous queue and member item variable is updated
     */
    protected abstract TimeEvent waiting(double currentTime);

    /**
     * This abstract method is when the next queue is full. It should attempt to add the current item to the next queue
     * and if successful change status to WAITING. If unsuccessful, NOP
     * @param currentTime current simulation time
     * Precondition: busy method should have been called
     * Postcondition: If there is space in the next Queue, item will be added and status changed to WAITING. Else, NOP
     */
    protected abstract void blocked(double currentTime);

    /**
     * This abstract method is when the previous queue is empty. It should attempt to take an item from the queue and if
     * successfuly change status to waiting. If unsuccesful, NOP
     * @param currentTime current simulation time
     * Precondition: busy method should have been called
     * Postcondition: If there is an Item in the previous Queue, status changed to WAITING. Else, NOP
     */
    protected abstract void starved(double currentTime);
}
