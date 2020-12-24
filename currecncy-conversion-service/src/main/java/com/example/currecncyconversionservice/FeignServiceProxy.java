package com.example.currecncyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//declared name in application.properties 
//@FeignClient(name = "currency-conversion-service", url = "http://localhost:8000")
@FeignClient(name = "currency-conversion-service")
@RibbonClient(name = "currency-conversion-service")
public interface FeignServiceProxy {

	@GetMapping("exchange-currency/{from}/to/{to}")
	public ConversionBean exchangeValue(@PathVariable("from") String from, @PathVariable("to") String to) ;
}
