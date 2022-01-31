package ir.sk.iot.producer.model.event;

import ir.sk.iot.producer.utils.RandomUtils;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Value.Modifiable
public interface RandomEvent extends Event {

    @Override
    @Value.Default
    default OffsetDateTime getTimestamp() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Override
    @Value.Default
    default BigDecimal getValue() {
        return RandomUtils.randomBigDecimal();
    }

}
