package com.ogasimov.labs.springcloud.microservices.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @StreamListener(Sink.INPUT)
    public void persistEvent(Object payload) throws Exception {
        Event event = eventRepository.save(
            Event.builder()
                .payload(objectMapper.writeValueAsString(payload))
                .build());
        log.info("Event registered: {}", event);
    }

    public List<EventDto> getEvents(Integer startId, Integer count) {
        return eventRepository.findAllByIdBetween(startId, count).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private EventDto toDto(Event event) {
        return EventDto.builder()
            .eventId(event.getId())
            .payload(event.getPayload())
            .build();
    }
}
