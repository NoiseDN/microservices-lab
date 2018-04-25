package com.ogasimov.labs.springcloud.microservices.order;

import com.ogasimov.labs.springcloud.microservices.common.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.AbstractStockCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.MinusStockCommand;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void publish(AbstractStockCommand command) {
        output.stock().send(MessageBuilder.withPayload(command).build());
    }

    public void publish(AbstractBillCommand command) {
        output.bill().send(MessageBuilder.withPayload(command).build());
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
