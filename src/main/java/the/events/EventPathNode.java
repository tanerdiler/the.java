package the.events;

import java.io.Serializable;

interface EventPathNode extends Serializable {
    void onEvent(EventInfo source);
}

