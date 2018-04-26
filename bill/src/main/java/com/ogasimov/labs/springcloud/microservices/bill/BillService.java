package com.ogasimov.labs.springcloud.microservices.bill;

import com.ogasimov.labs.springcloud.microservices.common.command.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.CreateBillCommand;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @StreamListener(Sink.INPUT)
    public void createBill(AbstractBillCommand command) {
        if (command instanceof CreateBillCommand) {
            createBill(command.getTableId(), ((CreateBillCommand) command).getOrderId());
        }
    }

    public void createBill(Integer tableId, Integer orderId) {
        Bill bill = new Bill();
        bill.setTableId(tableId);
        bill.setOrderId(orderId);
        Bill saved = billRepository.save(bill);
        log.info("Bill {} created for table {} and order {}", saved.getId(), tableId, orderId);
    }

    public void payBills(Integer tableId) {
        List<Bill> bills = billRepository.findAllByTableId(tableId);
        if (bills.isEmpty()) {
            throw new EntityNotFoundException("Bills not found");
        }
        billRepository.delete(bills);
        log.info("All bills for table {} were paid", tableId);
    }
}
