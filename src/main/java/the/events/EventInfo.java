package the.events;

import static com.google.common.collect.Maps.newHashMap;
import static the.helper.Helper.isSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


public class EventInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final Map<String, Parameter> params = newHashMap();

    private EventInfo(Parameter ... parameters) {
        for (Parameter param :  parameters) {
            params.put(param.name, param);
        }
    }

    private EventInfo(Collection<Parameter> parameters) {
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

    public static final EventInfo aNew( Parameter ... parameters)
    {
        return new EventInfo(parameters);
    }

    public EventInfo clone () {
        Map<String, Parameter> newParams = newHashMap(params);
        EventInfo newEventSource = new EventInfo(newParams.values());
        return newEventSource;
    }
    
    public EventInfo add (Parameter parameter) {
        params.put(parameter.name, parameter);
        return this;
    }
}
