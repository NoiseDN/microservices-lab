package com.ogasimov.labs.springcloud.microservices.order;

import com.ogasimov.labs.springcloud.microservices.common.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.AbstractOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.AbstractStockCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.MinusStockCommand;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @Autowired
    private BillClient billClient;

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
        orderRepository.save(order);

        final Integer orderId = order.getId();
//        stockClient.minusFromStock(menuItems);
        publish(new MinusStockCommand(menuItems));
//        billClient.createBill(tableId, orderId);
        publish(new CreateBillCommand(tableId, orderId));

        return orderId;
    }
}
