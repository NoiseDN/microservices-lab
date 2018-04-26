package com.ogasimov.labs.springcloud.microservices.common.event;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StartDinnerEvent implements Event {

    private final List<Integer> menuItems;

}
