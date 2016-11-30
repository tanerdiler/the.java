package the.events;

public class Event {
    public static boolean LOG_EVENTBRAKER = false;

    private EventPath eventPath = EventPath.mainPath();

    private EventInfo eventInfo;

    private final EventType eventType;

    private Event(EventType eventType) {
        this.eventType = eventType;
        this.eventInfo = EventInfo.aNew();
    }

    public Event add(EventListener listener) {
        eventPath.add(listener);
        return this;
    }

    public Event add(EventPath subPath) {
        eventPath.add(subPath);
        return this;
    }

    public EventInfo fire(Parameter... parameters) {
        eventInfo = EventInfo.aNew(parameters);
        eventPath.onEvent(eventInfo);
        return eventInfo;
    }


    public static Event aNew(EventType eventType) {
        return new Event(eventType);
    }

    protected EventType getEventType() {
        return eventType;
    }
}
