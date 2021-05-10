import java.util.ArrayList;

public class Item
{
    private String uniqueID;
    private ArrayList<Process> processes;

    public Item()
    {
        processes = new ArrayList<>();
    }

    public void setID(String uniqueID_)
    {
        uniqueID = uniqueID_;
    }

    public String getID()
    {
        return uniqueID;
    }

    public void addProcess(Process inData)
    {
        processes.add(inData);
    }

    public double getLastProcessEndTime()
    {
        return processes.get(processes.size() - 1).getEndTime();
    }
}
