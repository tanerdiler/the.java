package the.events;

import static com.google.common.collect.Maps.newHashMap;
import static the.helper.Helper.isSet;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Events
{
    private static final Map<EventType, Event> events = newHashMap();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReadLock readLock = lock.readLock();
    private static final WriteLock writeLock = lock.writeLock();
    
    private static final Event get (EventType eventType) {
        Event event = null;
        readLock.lock();
        try {
            event = events.get(eventType);
        } finally {
            readLock.unlock();
        }
        return event;
    }
    
    private static final void put (Event event) {
        writeLock.lock();
        try {
        events.put(event.getEventType(), event);
        } finally {
            writeLock.unlock();
        }
    }
    
    public static final Event event (EventType type) {
        Event event = get(type);
        if (event == null) {
            event = Event.aNew(type);
            put(event);
        }
        return event;
    }

    public static final void reset () {
        events.clear();
    }
}
