import java.util.LinkedList;
import java.util.Queue;

public class StorageQueue<T extends Item>
{
    private final int Qmax;
    private final String id;
    Queue<T> items;

    private double itemsInQueueRecord;
    private double oldTime;

    private int thruput;
    private double timeInQueueRecord;

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

    public Item remove(double currentTime)
    {
        // Record for average items in queue
        double timeDifference = currentTime - oldTime;
        oldTime = currentTime;
        itemsInQueueRecord += timeDifference * size();

        Item temp = items.remove();
        thruput++;
        timeInQueueRecord += currentTime - temp.getLastProcessEndTime();

        return temp;
    }

    public boolean add(T item_)
    {
        if (items.size() < Qmax) {
            // Record item count
            double currentTime = item_.getLastProcessEndTime();
            double timeDifference = currentTime - oldTime;
            oldTime = currentTime;
            itemsInQueueRecord += timeDifference * size();

            items.add(item_);

            return true;
        }
        return false;
    }

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
