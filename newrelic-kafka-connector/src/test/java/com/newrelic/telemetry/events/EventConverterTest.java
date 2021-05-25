package com.newrelic.telemetry.events;

import com.newrelic.telemetry.Attributes;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EventConverterTest {

    Fixtures fixtures;

    @Before
    public void setUp() throws Exception {
        this.fixtures = new Fixtures();
    }

    void testEquals(Event createdEvent) {
        Map<String, Object> expected = new HashMap<>();

        expected.put("metadata.kafkaTopic", "myTopic");
        expected.put("metadata.kafkaPartition", "0");
        expected.put("metadata.kafkaOffset", 1001L);
        expected.put("stringField", "stringValue");
        expected.put("intField", 10);
        expected.put("floatField", 9.9f);
        expected.put("flattened.field.name", "someStringValue");

        assertEquals("myTestEvent", createdEvent.getEventType());
        assertEquals(1621466257L, createdEvent.getTimestamp());

        Attributes attributes = createdEvent.getAttributes();
        assertEquals(expected, attributes.asMap());


    }

    @Test
    public void withSchema() {
        Event testEvent = EventConverter.toNewRelicEvent(this.fixtures.sampleStructRecord);
        testEquals(testEvent);
    }

//    @Test
//    public void withoutSchema() {
//        Event testEvent = EventConverter.toNewRelicEvent(sampleSchemalessRecord);
//        testEquals(testEvent);
//    }
}