package com.ogasimov.labs.springcloud.microservices.guest;

import com.ogasimov.labs.springcloud.microservices.common.command.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.AbstractOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.AbstractTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.OccupyTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.PayBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.event.FinishDinnerEvent;
import com.ogasimov.labs.springcloud.microservices.common.event.StartDinnerEvent;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DinnerService {

    @Autowired
    private TableClient tableClient;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private CommunicationChannel output;

    private Random random = new Random();

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
        log.info("Dinner started for menu items: {}", menuItems);
        eventClient.storeEvent(StartDinnerEvent.builder().menuItems(menuItems).build());
        //check free tables
        List<Integer> freeTables = tableClient.getFreeTables();
        if (freeTables.size() == 0) {
            throw new RuntimeException("No free tables available.");
        }

        final Integer tableId = randomTable(freeTables);
        publish(new OccupyTableCommand(tableId));
        publish(new CreateOrderCommand(tableId, menuItems));

        return tableId;
    }

    private Integer randomTable(List<Integer> freeTables) {
        int index = random.nextInt(freeTables.size());
        return freeTables.get(index);
    }

    public void finishDinner(Integer tableId) {
        log.info("Dinner finished for table {}", tableId);
        eventClient.storeEvent(FinishDinnerEvent.builder().tableId(tableId).build());
        publish(new PayBillCommand(tableId));
    }
}
