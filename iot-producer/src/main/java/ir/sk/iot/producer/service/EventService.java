package ir.sk.iot.producer.service;

import ir.sk.iot.producer.controller.events.request.EventRequest;
import ir.sk.iot.producer.mapper.EventMapper;
import ir.sk.iot.producer.model.event.Event;
import ir.sk.iot.producer.stream.producer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

    private final EventProducer eventProducer;
    private final EventMapper eventMapper;

    public EventService(EventProducer eventProducer, EventMapper eventMapper) {
        this.eventProducer = eventProducer;
        this.eventMapper = eventMapper;
    }

    public ParallelFlux<Void> processAllEvents(List<EventRequest> events) {
        return Flux.fromIterable(events)
                .doFirst(() -> LOG.debug("=== Starting event generator. -> {} ", LocalDateTime.now()))
                .parallel(events.size())
                .runOn(Schedulers.boundedElastic())
                .flatMap(event -> processEvent(eventMapper.fromRequest(event), event.getTotal(), event.getHeartBeat()))
                .doOnComplete(() -> LOG.info("==== Event generation ended {}", LocalDateTime.now()));
    }

    public Flux<Void> processEvent(Event event, Integer total, Integer heartBeat) {
        return Flux.range(0, total)
                .doFirst(() -> LOG.debug("==== Going to process event single event -> {}", event))
                .delayElements(Duration.ofSeconds(heartBeat))
                .flatMap(processSequence -> sendEvent(event))
                .doOnComplete(() -> LOG.debug("==== Going to process event single event -> {}", event));
    }

    protected Mono<Void> sendEvent(Event event) {
        return eventProducer.sendEvent(event)
                .doFirst(() -> LOG.debug("==== Sending event {}", event));
    }
}
