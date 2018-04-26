package com.ogasimov.labs.springcloud.microservices.stock;

import com.ogasimov.labs.springcloud.microservices.common.command.AbstractStockCommand;
import com.ogasimov.labs.springcloud.microservices.common.command.MinusStockCommand;

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
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @StreamListener(Sink.INPUT)
    public void minus(AbstractStockCommand command) {
        if (command instanceof MinusStockCommand) {
            minusFromStock(command.getMenuItems());
        }
    }

    public void minusFromStock(List<Integer> menuItems) {
        menuItems.forEach(menuItemId -> {
            Stock stock = stockRepository.findOneByMenuItemId(menuItemId);
            if (stock == null) {
                throw  new EntityNotFoundException("Stock not found: " + menuItemId);
            }
            if (stock.getCount() == 0) {
                throw  new EntityNotFoundException("Stock empty: " + menuItemId);
            }
            stock.setCount(stock.getCount() - 1);
            stockRepository.save(stock);
            log.info("Menu item {} was removed from Stock. Remaining stock: ", menuItemId, stock.getCount());
        });

    }
}
