package com.ogasimov.labs.springcloud.microservices.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
class EventDto {

    private Integer eventId;

    private String payload;
    
}
