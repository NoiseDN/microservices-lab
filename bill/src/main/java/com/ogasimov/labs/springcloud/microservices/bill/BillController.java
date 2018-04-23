package com.ogasimov.labs.springcloud.microservices.bill;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/bill/{tableId}/{orderId}")
    @HystrixCommand
    public void createBill(@PathVariable("tableId") Integer tableId,
                           @PathVariable("orderId") Integer orderId) {
        billService.createBill(tableId, orderId);
    }

    @DeleteMapping("/bills/{tableId}")
    @HystrixCommand
    public void payBills(@PathVariable("tableId") Integer tableId) {
        billService.payBills(tableId);
    }
}
