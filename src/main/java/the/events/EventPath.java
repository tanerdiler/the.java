package the.events;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventPath implements EventPathNode
{
    private static final String STOPPING_ON_TRIGGERING_METHODS = "Stopping triggering rest of listeners for %s. Reason : {%s}";
    
    private static final Logger logger = LogManager.getLogger(Event.class);
    
    private List<EventPathNode> listeners = newArrayList();
    
    public EventPath add (EventListener listener) {
        listeners.add(EventPathListenerNode.wrap(listener));
        return this;
    }

    public EventPath add(EventPath subPath)
    {
        listeners.add(subPath);
        return this;
    }

    public void execute(EventSource source, String methodName)
    {
        for (EventPathNode listener : listeners) {
            try
            {
                listener.execute(source, methodName);
            }
            catch (ListenerTriggeringBreakerException e)
            {
                if (Event.LOG_EVENTBRAKER) {
                    logger.warn(format(STOPPING_ON_TRIGGERING_METHODS, methodName, e.getMessage()));
                }
                break;
            }
        }
    }
    
    public static EventPath mainPath () {
        return new EventPath();
    }
    
    public static EventPath subPath () {
        return new EventPath();
    }
}
