import java.util.ArrayList;

public class Item
{
    private String uniqueID;
    private ArrayList<ProcessEvent> processEvents;

    public Item()
    {
        processEvents = new ArrayList<>();
        uniqueID = IdHandler.getID();
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

    public ArrayList<ProcessEvent> report()
    {
        return processEvents;
    }
}
