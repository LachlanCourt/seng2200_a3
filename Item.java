import java.util.ArrayList;

public class Item
{
    private String uniqueID;
    private ArrayList<ProcessEvent> processEvents;

    public Item()
    {
        processEvents = new ArrayList<>();
    }

    public void setID(String uniqueID_)
    {
        uniqueID = uniqueID_;
    }

    public String getID()
    {
        return uniqueID;
    }

    public void addProcess(ProcessEvent inData)
    {
        processEvents.add(inData);
    }

    public double getLastProcessEndTime()
    {
        return processEvents.get(processEvents.size() - 1).getEndTime();
    }

    public double getStartProcessEndTime()
    {
        return processEvents.get(processEvents.size() - 1).getStartTime();
    }
}
