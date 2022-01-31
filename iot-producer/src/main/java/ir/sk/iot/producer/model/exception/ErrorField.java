package ir.sk.iot.producer.model.exception;

import org.immutables.value.Value;

@Value.Immutable
public interface ErrorField {
    String getField();

    String getDescription();
}
