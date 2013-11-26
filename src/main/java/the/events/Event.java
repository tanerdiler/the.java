package the.events;

public class Event
{
    public static boolean LOG_EVENTBRAKER = false;
    
    public final EventType type;
    
    private EventPath mainPath = EventPath.mainPath();

    private Event (EventType event) {
        this.type = event;
    }

    public Event add (EventListener listener) {
        mainPath.add(listener);
        return this;
    }
    public Event add (EventPath subPath) {
        mainPath.add(subPath);
        return this;
    }
    
    public void fire (EventSource source) {
        mainPath.execute(source, type.methodName);
    }
    
    public void fire (Parameter ... parameters) {
        EventSource source = EventSource.aNew(type, parameters);
        fire(source);
    }

    public static Event aNew(EventType eventType)
    {
        return new Event(eventType);
    }
}
