package com.ogasimov.labs.springcloud.microservices.guest;

import com.ogasimov.labs.springcloud.microservices.common.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.AbstractOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.AbstractTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.OccupyTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.PayBillCommand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class DinnerService {
    @Autowired
    private TableClient tableClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private BillClient billClient;

    @Autowired
    private CommunicationChannel output;

    private void publish(AbstractTableCommand command) {
        if (command instanceof OccupyTableCommand) {
            output.table().send(MessageBuilder.withPayload(command).build());
        }
    }

    private void publish(AbstractOrderCommand command) {
        if (command instanceof CreateOrderCommand) {
            output.order().send(MessageBuilder.withPayload(command).build());
        }
    }

    private void publish(AbstractBillCommand command) {
        if (command instanceof PayBillCommand) {
            output.bill().send(MessageBuilder.withPayload(command).build());
        }
    }

    public Integer startDinner(List<Integer> menuItems) {
        //check free tables
        List<Integer> freeTables = tableClient.getFreeTables();
        if (freeTables.size() == 0) {
            throw new RuntimeException("No free tables available.");
        }

        //occupy a table
        final Integer tableId = freeTables.get(0);
//        tableClient.occupyTable(tableId);
        publish(new OccupyTableCommand(tableId));

        //create the order
//        orderClient.createOrder(tableId, menuItems);
        publish(new CreateOrderCommand(tableId, menuItems));

        return tableId;
    }

    public void finishDinner(Integer tableId) {
//        billClient.payBills(tableId);
        publish(new PayBillCommand(tableId));
    }
}
