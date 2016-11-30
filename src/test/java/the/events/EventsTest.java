package the.events;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class EventsTest {

    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicInteger paramCounter = new AtomicInteger();

    public class TestListener implements EventListener {


        public void onEvent(EventInfo source) {
            counter.getAndIncrement();
            if (((String) source.get("name")).equals("tanerdiler")) {
                paramCounter.getAndIncrement();
            }
        }

    }

    @After
    public void tearDown() {
        Events.reset();
    }

    @Test
    public void shouldFireWithoutParameter() {
        Events.event(TestEventType.VISITORLOGON).add(new TestListener()).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(1, counter.get());
        Assert.assertEquals(1, paramCounter.get());
    }

    @Test
    public void shouldFireBySingleParameter() {
        Events.event(TestEventType.VISITORLOGON).add(new TestListener());

        EventInfo source = Events.event(TestEventType.VISITORLOGON).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(source.get("name"), "tanerdiler");
    }

    @Test
    public void shouldFireByMultipleParameter() {
        Events.event(TestEventType.VISITORLOGON).add(new TestListener());

        EventInfo source = Events
                .event(TestEventType.VISITORLOGON)
                .fire(Parameter.by("name", "taner"), Parameter.by("surname", "diler"));

        Assert.assertEquals(source.get("name"), "taner");
        Assert.assertEquals(source.get("surname"), "diler");
    }

    @Test
    public void triggerListenersOnMainPath() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> counter.getAndIncrement())
                .add(source -> counter.getAndIncrement());

        Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals(2, counter.get());
    }

    @Test
    public void triggerSubPathListenersToo() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> counter.getAndIncrement())
                .add(EventPath.subPath()
                    .add(source -> counter.getAndIncrement())
                    .add(source -> counter.getAndIncrement()))
                .add(source -> counter.getAndIncrement());

        Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals(4, counter.get());
    }

    @Test
    public void dontEffectMainPathExecutionWhenTriggerBreakerThrownBySubPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> counter.getAndIncrement())
                .add(EventPath.subPath()
                    .add(source -> { throw ListenerTriggeringBreakerException.aNew("Break subpath triggering");})
                    .add(source -> counter.getAndIncrement()))
                .add(source -> counter.getAndIncrement());

        Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals(2, counter.get());
    }

    @Test
    public void shouldExecuteSubPathNodeUntilPathBroken() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> counter.getAndIncrement())
                .add(EventPath.subPath()
                        .add(source -> counter.getAndIncrement())
                        .add(source -> counter.getAndIncrement())
                        .add(source -> { throw ListenerTriggeringBreakerException.aNew("Break subpath triggering");})
                        .add(source -> counter.getAndIncrement()))
                .add(source -> counter.getAndIncrement());

        Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals(4, counter.get());
    }

    @Test
    public void keepContinueOnMainPathWhenTriggerBreakerThrownBySubPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> counter.getAndIncrement())
                .add(EventPath.subPath()
                    .add(source -> {throw ListenerTriggeringBreakerException.aNew("Break subpath triggering");})
                    .add(source -> counter.getAndIncrement()))
                .add(source -> counter.getAndIncrement());

        Events.event(TestEventType.VISITORLOGON).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(2, counter.get());
    }

    @Test
    public void shouldAddNewParametersWhileExecutingEvent() {

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> source.add(Parameter.by("pipe1","1")))
                .add(source -> source.add(Parameter.by("pipe2","2")));

        EventInfo eventInfo = Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals("1", eventInfo.get("pipe1"));
        Assert.assertEquals("2", eventInfo.get("pipe2"));
    }

    @Test
    public void shouldAddExtraParametersWhileExecutingEvent() {

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> source.add(Parameter.by("pipe1","1")))
                .add(source -> source.add(Parameter.by("pipe2","2")));

        EventInfo eventInfo = Events.event(TestEventType.VISITORLOGON).fire(Parameter.by("pipe0","0"));

        Assert.assertEquals("0", eventInfo.get("pipe0"));
        Assert.assertEquals("1", eventInfo.get("pipe1"));
        Assert.assertEquals("2", eventInfo.get("pipe2"));
    }

    @Test
    public void shouldUpdateParameterValueWhileExecutingEvent() {

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> source.add(Parameter.by("param","updatedvalue")));

        EventInfo eventInfo = Events.event(TestEventType.VISITORLOGON).fire(Parameter.by("param", "value"));

        Assert.assertEquals("updatedvalue", eventInfo.get("param"));
    }



    @Test
    public void breakTheEventTriggeringAfterRuntimeExceptionThrown() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> {throw new RuntimeException();})
                .add(EventPath.subPath()
                        .add(source -> counter.getAndIncrement())
                        .add(source -> counter.getAndIncrement()))
                .add((EventListener) source -> counter.getAndIncrement());

        try {
            Events.event(TestEventType.VISITORLOGON).fire();
        } catch (Exception e) {
        }
        Assert.assertEquals(0, counter.get());
    }

    @Test
    public void whenExecutingSubPath_AddingNewParameterToEventInfo_ShouldNotAffectMainPathEventInfo() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON)
                .add(source -> source.add(Parameter.by("mainpathparam1", "mainpathvalue1")))
                .add(EventPath.subPath()
                        .add(source -> source.add(Parameter.by("subpathparam1", "subpathvalue1")))
                        .add(source -> source.add(Parameter.by("subpathparam2", "subpathvalue2"))))
                .add(source -> source.add(Parameter.by("mainpathparam2", "mainpathvalue2")));


        EventInfo eventInfo = Events.event(TestEventType.VISITORLOGON).fire();

        Assert.assertEquals("mainpathvalue1", eventInfo.get("mainpathparam1"));
        Assert.assertEquals("mainpathvalue2", eventInfo.get("mainpathparam2"));
        Assert.assertNull(eventInfo.get("subpathparam1"));
        Assert.assertNull(eventInfo.get("subpathparam2"));

    }

}
