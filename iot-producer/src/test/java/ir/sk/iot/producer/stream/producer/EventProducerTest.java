package ir.sk.iot.producer.stream.producer;

import ir.sk.iot.producer.model.event.Event;
import ir.sk.iot.producer.model.event.EventSub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventProducerTest {

    private EventProducer testClass;
    private MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        EventDataOutput dataOutput = mock(EventDataOutput.class);
        messageChannel = mock(MessageChannel.class);

        when(messageChannel.send(any())).thenReturn(true);
        when(dataOutput.output()).thenReturn(messageChannel);

        testClass = new EventProducer(dataOutput);
    }

    @Test
    void testBuildMessage() {
        final Message<Event> actual = testClass.buildMessage(new EventSub());

        assertEquals(12345L, actual.getHeaders().get(KafkaHeaders.MESSAGE_KEY));
    }

    @Test
    void testSendEvent() {


        StepVerifier.create(testClass.sendEvent(new EventSub())).verifyComplete();
        verify(messageChannel, times(1)).send(any());
    }
}