/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is purely designed for reporting at the end of the production
 ****    run. It stores information about an Item going through a single stage
 *******************************************************************************/
public class ProcessEvent
{
    // Instance variables
    private double startTime;
    private double endTime;
    private String stageID;

    // Main Constructor
    public ProcessEvent(double startTime_, double endTime_, String stageID_)
    {
        startTime = startTime_;
        endTime = endTime_;
        stageID = stageID_;
    }

    //----- GETTERS -----//
    public double getEndTime()
    {
        return endTime;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public String getStageID()
    {
        return stageID;
    }
}
