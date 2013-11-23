package the.events;

import static com.google.common.collect.Maps.newHashMap;
import static the.helper.Helper.isSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


public class EventSource implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final Map<String, Parameter> params = newHashMap();

    private final EventType type;
    
    private EventSource (EventType type, Parameter ... parameters) {
        this.type = type;
        for (Parameter param :  parameters) {
            params.put(param.name, param);
        }
    }
    
    private EventSource (EventType type, Collection<Parameter> parameters) {
        this.type = type;
        for (Parameter param :  parameters) {
            params.put(param.name, param);
        }
    }

    public Object get(String key)
    {
        Parameter param = params.get(key);
        if (isSet(param)) {
            return param.value;
        }
        return null;
    }
    
    public EventType eventType () {
        return type;
    }
    
    public String eventName () {
        return type.name();
    }
    
    public static final EventSource aNew(EventType type, Parameter ... parameters)
    {
        return new EventSource(type, parameters);
    }
    
    public EventSource clone () {
        Map<String, Parameter> newParams = newHashMap(params);
        EventSource newEventSource = new EventSource(type, newParams.values());
        return newEventSource;
    }
    
    public EventSource add (Parameter parameter) {
        params.put(parameter.name, parameter);
        return this;
    }
}
