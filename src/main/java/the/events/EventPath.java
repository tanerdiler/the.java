package the.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

class EventPath implements EventPathNode {
    private static final String STOPPING_ON_TRIGGERING_METHODS = "Stopping triggering rest of listeners. Reason : {%s}";

    private static final Logger logger = LogManager.getLogger(Event.class);

    private List<EventPathNode> listeners = newArrayList();

    public EventPath add(EventListener listener) {
        listeners.add(listener);
        return this;
    }

    public EventPath add(EventPathNode subPath) {
        listeners.add(subPath);
        return this;
    }

    @Override
    public void onEvent(EventInfo source) {
        for (EventPathNode node : listeners) {
            try {
                if (node instanceof EventListener) {
                    node.onEvent(source);
                } else if (node instanceof EventPath) {
                    node.onEvent(source.clone());
                }
            } catch (ListenerTriggeringBreakerException e) {
                if (Event.LOG_EVENTBRAKER) {
                    logger.warn(format(STOPPING_ON_TRIGGERING_METHODS, e.getMessage()));
                }
                break;
            }
        }
    }


    public static EventPath mainPath() {
        return new EventPath();
    }

    public static EventPath subPath() {
        return new EventPath();
    }
}
