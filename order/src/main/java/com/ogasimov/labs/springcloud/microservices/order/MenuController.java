package com.ogasimov.labs.springcloud.microservices.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    @HystrixCommand
    public Map<Integer, String> getMenu() {
        return menuService.getMenuItems();
    }
}
