import java.util.Deque;

public abstract class Stage
{
    protected String status = "waiting";
    protected Item item;

    protected double Qmax;
    protected Deque<Item> prev;
    protected Deque<Item> next;

    public abstract TimeEvent process(double currentTime, double processingTime);
}
