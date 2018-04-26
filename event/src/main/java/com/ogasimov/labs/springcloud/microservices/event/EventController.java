package com.ogasimov.labs.springcloud.microservices.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/event")
    @HystrixCommand
    public void storeEvent(@RequestBody Object payload) throws Exception {
        eventService.persistEvent(payload);
    }

    @GetMapping("/events/{startId}/{count}")
    @HystrixCommand
    public List<EventDto> getEvents(@PathVariable("startId") Integer startId,
                                    @PathVariable("count") Integer count) {
        return eventService.getEvents(startId, count);
    }
}
