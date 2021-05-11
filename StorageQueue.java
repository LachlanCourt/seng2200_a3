import java.util.LinkedList;
import java.util.Queue;

public class StorageQueue<T extends Item>
{
    private int Qmax;
    Queue<T> items;

    public StorageQueue(int Qmax_)
    {
        Qmax = Qmax_;
        items = new LinkedList<>();
    }

    public Item remove()
    {
        return items.remove();
    }

    public boolean add(T item_)
    {
        if (items.size() < Qmax) {
            items.add(item_);
            return true;
        }
        return false;
    }

    public int size()
    {
        return items.size();
    }


}
