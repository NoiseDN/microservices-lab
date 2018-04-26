package com.ogasimov.labs.springcloud.microservices.order;

import com.ogasimov.labs.springcloud.microservices.common.command.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.AbstractOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.AbstractStockCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.CreateBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.MinusStockCommand;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommunicationChannel output;

    private void publish(AbstractStockCommand command) {
        output.stock().send(MessageBuilder.withPayload(command).build());
    }

    private void publish(AbstractBillCommand command) {
        output.bill().send(MessageBuilder.withPayload(command).build());
    }

    @StreamListener("order")
    public void createOrder(AbstractOrderCommand command) {
        if (command instanceof CreateOrderCommand) {
            createOrder(command.getTableId(), ((CreateOrderCommand) command).getMenuItems());
        }
    }

    public Integer createOrder(Integer tableId, List<Integer> menuItems) {
        Order order = new Order();
        order.setTableId(tableId);
        Order saved = orderRepository.save(order);

        final Integer orderId = order.getId();

        publish(new MinusStockCommand(menuItems));
        publish(new CreateBillCommand(tableId, orderId));

        log.info("Order {} for table {} created", saved.getId(), tableId);

        return orderId;
    }
}
