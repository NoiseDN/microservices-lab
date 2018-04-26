package com.ogasimov.labs.springcloud.microservices.guest;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CommunicationChannel {

    @Output
    MessageChannel bill();

    @Output
    MessageChannel order();

    @Output
    MessageChannel table();

}
