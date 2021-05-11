public abstract class Stage
{
    protected String status = "waiting";
    protected Item item;

    protected StorageQueue<Item> prev;
    protected StorageQueue<Item> next;

    public TimeEvent process(double currentTime, double processingTime)
    {
        TimeEvent temp = null;
        if (status.compareTo("busy") == 0)
        {busy(currentTime);
        }
        if (status.compareTo("waiting") == 0)
        {
            temp = waiting(currentTime, processingTime);
        }
        if (status.compareTo("blocked") == 0)
        {
            blocked();
        }
        if (status.compareTo("starved") == 0)
        {
            starved();
        }
        return temp;
    }

    protected abstract void busy(double currentTime);
    protected abstract TimeEvent waiting(double currentTime, double processingTime);
    protected abstract void blocked();
    protected abstract void starved();

}
