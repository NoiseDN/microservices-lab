package com.ogasimov.labs.springcloud.microservices.guest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("Bill")
public interface BillClient {
    @PostMapping("/bills/{tableId}")
    void payBills(@PathVariable("tableId") Integer tableId);
}
