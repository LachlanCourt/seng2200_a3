import java.util.Deque;

public abstract class Stage
{
    protected boolean busy = false;
    protected Item item;

    protected Deque<Item> prev;
    protected Deque<Item> next;

    public abstract TimeEvent process(double currentTime, double processingTime);
}
