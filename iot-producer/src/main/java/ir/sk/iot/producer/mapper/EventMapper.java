package ir.sk.iot.producer.mapper;

import ir.sk.iot.producer.controller.events.request.ClusterEventRequest;
import ir.sk.iot.producer.controller.events.request.EventRequest;
import ir.sk.iot.producer.model.event.ModifiableRandomEvent;
import ir.sk.iot.producer.model.event.RandomEvent;
import ir.sk.iot.producer.utils.RandomUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    default RandomEvent fromRequest(ClusterEventRequest request, Integer processStep) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setClusterId(request.getClusterId())
                .setName(String.format("%s_%s", request.getName(), processStep))
                .setId(RandomUtils.randomInt(request.getClusterSize()));
    }

    default RandomEvent fromRequest(EventRequest request) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setName(request.getName())
                .setId(request.getId())
                .setClusterId(request.getClusterId());
    }

}
