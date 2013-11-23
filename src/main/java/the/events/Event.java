package the.events;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Event
{
    private static final String EXCEPTION_ON_TRIGGERING_METHOD = "Exception on triggering %s  method of %s ";
    private static final String STOPPING_ON_TRIGGERING_METHODS = "Stopping triggering rest of listeners for %s. Reason : {%s}";

    private static final Logger logger = LoggerFactory.getLogger(Event.class);
    
    public static boolean LOG_EVENTBRAKER = false;
    
    public final EventType type;
    
    private List<EventListener> listeners = newArrayList();

    private Event (EventType event) {
        this.type = event;
    }
    
    public Event add (EventListener listener) {
        listeners.add(listener);
        return this;
    }
    
    public Event remove (EventListener listener) {
        listeners.remove(listener);
        return this;
    }
    
    public void fire (EventSource source) {
        for (EventListener listener : listeners) {
            Class<?> clazz = listener.getClass();
            try
            {
                try {
                    Method method = clazz.getMethod(type.methodName, EventSource.class);
                    method.invoke(listener, source);
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof ListenerTriggeringBreakerException) {
                        throw (ListenerTriggeringBreakerException) e.getCause();
                    } else {
                        throw e;
                    }
                }
            }
            catch (ListenerTriggeringBreakerException e)
            {
                if (LOG_EVENTBRAKER) {
                    logger.warn(format(STOPPING_ON_TRIGGERING_METHODS, type.methodName, e.getMessage()));
                }
                break;
            }
            catch (Exception e)
            {
                throw new RuntimeException(format(EXCEPTION_ON_TRIGGERING_METHOD, type.methodName, clazz.getName()), e);
            }
        }
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
