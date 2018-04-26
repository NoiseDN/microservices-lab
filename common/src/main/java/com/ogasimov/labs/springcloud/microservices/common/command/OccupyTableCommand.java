package com.ogasimov.labs.springcloud.microservices.common.command;

public class OccupyTableCommand extends AbstractTableCommand {
    public OccupyTableCommand(Integer tableId) {
        super(tableId);
    }
}
