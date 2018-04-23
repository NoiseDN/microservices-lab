package com.ogasimov.labs.springcloud.microservices.guest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {
    @Autowired
    private DinnerService dinnerService;

    @PostMapping("/dinner")
    @HystrixCommand //(fallbackMethod = "startDinnerFallback")
    public Integer startDinner(@RequestBody List<Integer> menuItems) {
        return dinnerService.startDinner(menuItems);
    }

//    private Integer startDinnerFallback(List<Integer> menuItems) {
//        return 0;
//    }

    @DeleteMapping("/dinner/{tableId}")
    @HystrixCommand
    public void finishDinner(@PathVariable Integer tableId) {
        dinnerService.finishDinner(tableId);
    }
}
