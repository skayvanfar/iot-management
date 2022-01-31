package ir.sk.iot.producer.controller.events.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ir.sk.iot.producer.model.constant.EventsConstant;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableEventRequest.Builder.class)
public interface EventRequest {

    /*It needed nullable and notnull, otherwise it would throw java.lang.IllegalStateException
     and we cannot get the proper message.*/
    @Positive(message = "{invalid.request.total.positive}")
    @NotNull(message = "{mandatory.request.total}")
    @Nullable
    Integer getTotal();

    @NotNull(message = "{mandatory.request.type}")
    @Nullable
    String getType();

    @Max(value = EventsConstant.MAX_HEART_BEAT, message = "{invalid.event.total.heartBeat}")
    @Min(value = EventsConstant.MIN_HEART_BEAT, message = "{invalid.event.total.heartBeat}")
    @Value.Default
    default Integer getHeartBeat() {
        return EventsConstant.DEFAULT_HEART_BEAT;
    }

    @Positive(message = "{invalid.event.request.total.id}")
    @NotNull(message = "{mandatory.event.request.id}")
    @Nullable
    Long getId();

    @Nullable
    @NotBlank(message = "{mandatory.request.name}")
    String getName();

    @Nullable
    Long getClusterId();

}
