package the.events;

import java.io.Serializable;

public class Parameter implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected final String name;
    protected final Object value;

    private Parameter(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }
    
    public static final <T> Parameter by(String name, Object value)
    {
        return new Parameter(name, value);
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
