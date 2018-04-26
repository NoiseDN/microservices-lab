package com.ogasimov.labs.springcloud.microservices.common.command;

import java.util.List;

public class MinusStockCommand extends AbstractStockCommand {
    public MinusStockCommand(List<Integer> menuItems) {
        super(menuItems);
    }
}
