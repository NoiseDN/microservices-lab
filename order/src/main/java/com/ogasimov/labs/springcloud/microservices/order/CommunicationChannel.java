package com.ogasimov.labs.springcloud.microservices.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CommunicationChannel {

    @Input
    SubscribableChannel order();

    @Output
    MessageChannel bill();

    @Output
    MessageChannel stock();

}
