/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class represents an Item being created on the ProductionLine. It
 ****    keeps a list of ProcessEvents as it goes through Stages
 *******************************************************************************/
import java.util.ArrayList;

public class Item
{
    // Instance variables
    private String uniqueID;
    private ArrayList<ProcessEvent> processEvents;

    // Default Constructor
    public Item()
    {
        processEvents = new ArrayList<>();
        uniqueID = IdHandler.getID();
    }

    // Get the ID of the Item
    public String getID()
    {
        return uniqueID;
    }

    // Add a process event to the ArrayList
    public void addProcess(ProcessEvent inData)
    {
        processEvents.add(inData);
    }

    // Return the end time of the last ProcessEvent in the ArrayList
    public double getLastProcessEndTime()
    {
        return processEvents.get(processEvents.size() - 1).getEndTime();
    }

    // Return the ArrayList of ProcessEvent's for the purposes of generating the final report
    public ArrayList<ProcessEvent> report()
    {
        return processEvents;
    }
}
