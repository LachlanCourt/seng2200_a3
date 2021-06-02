/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is used to determine when a Stage is due to finish producing
 ****    an Item. It implements Comarable so that it can be added to a PriorityQueue
 *******************************************************************************/
public class TimeEvent implements Comparable<TimeEvent>
{
    // Instance variable
    private double completionTime;

    // Main Constructor
    public TimeEvent(double completionTime_)
    {
        completionTime = completionTime_;
    }

    // Getter
    public double getCompletionTime()
    {
        return completionTime;
    }

    /**
     * Implement compareTo method
     * @param o another TimeEvent
     * @return a -1 or 1 to determine if the TimeEvent comes before or after o
     */
    @Override
    public int compareTo(TimeEvent o)
    {
        // Compare the times
        if (o.getCompletionTime() < this.completionTime)
        {
            return 1;
        }
        // If they are the same or o is > this
        return -1;
    }
}
