package com.ogasimov.labs.springcloud.microservices.table;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping("/tables")
    @HystrixCommand
    public List<Integer> getTables() {
        return tableService.getTableIds();
    }

    @GetMapping("/tables/free")
    @HystrixCommand
    public List<Integer> getFreeTables() {
        return tableService.getFreeTableIds();
    }

    @PutMapping("/table/{id}/free")
    @HystrixCommand
    public void freeTable(@PathVariable("id") Integer id) {
        tableService.updateTable(id, true);
    }

    @PutMapping("/table/{id}/occupy")
    @HystrixCommand
    public void occupyTable(@PathVariable("id") Integer id) {
        tableService.updateTable(id, false);
    }
}
