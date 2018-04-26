package com.ogasimov.labs.springcloud.microservices.common.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinishDinnerEvent implements Event {

    private final Integer tableId;

}
