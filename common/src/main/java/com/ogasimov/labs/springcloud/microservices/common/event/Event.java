package com.ogasimov.labs.springcloud.microservices.common.event;

public interface Event {
    default String getType() {
        return getClass().getSimpleName();
    }
}
