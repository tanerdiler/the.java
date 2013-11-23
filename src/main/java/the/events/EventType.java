package the.events;

public enum EventType
{
    VISITORLOGON("onVisitorLogon"),
    VISITORLOGOUT("onVisitorLogout");
    
    public final String methodName;

    private EventType (String methodName) {
        this.methodName = methodName;
    }
    
    public static EventType of (String name) {
        return valueOf(name.toUpperCase());
    }
}
