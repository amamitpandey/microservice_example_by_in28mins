package com.example.exchangeservice;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ExchangeController {

    @Autowired
    Environment environment;
    @Autowired
    ExchangeRepo exchangeRepo;

    @GetMapping("exchange-currency/{from}/to/{to}")
    public ExchangeBean exchangeCurrency(@PathVariable String from, @PathVariable String to) {
        ExchangeBean ExchangeBean = exchangeRepo.findByFromAndTo(from, to);
        ExchangeBean.setPort(Integer.parseInt(environment.getProperty("server.port")));
        return ExchangeBean;
    }

    @GetMapping("save-exchange-currency/{from}/to/{to}")
    public ExchangeBean SaveExchangeCurrency(@PathVariable String from, @PathVariable String to) {
        ExchangeBean ExchangeBean = new ExchangeBean();
        // to make unique and indentifiable have the port
        ExchangeBean.setFrom(from);
        ExchangeBean.setTo(to);
        ExchangeBean.setConversionMultiple(BigDecimal.valueOf(100));
        ExchangeBean.setPort(Integer.parseInt(environment.getProperty("server.port")));
        exchangeRepo.save(ExchangeBean);
        return ExchangeBean;
    }
}
