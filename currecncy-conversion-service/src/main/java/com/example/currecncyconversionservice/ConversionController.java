package com.example.currecncyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ConversionController {
	@Autowired
	FeignServiceProxy proxy;

	@GetMapping("/currency-conversion/{from}/to/{to}/quantity/{quantity}")
	public ConversionBean conversionFn(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> uriVarible = new HashMap<>();
		uriVarible.put("from", from);
		uriVarible.put("to", to);
		ResponseEntity<ConversionBean> restTemplate = new RestTemplate().getForEntity(
				"http://localhost:8000/exchange-currency/{from}/to/{to}", ConversionBean.class, uriVarible);
		ConversionBean response = restTemplate.getBody();
		BigDecimal total = quantity.multiply(response.getConversionMultiple()); // big decimal multiply like this
		return new ConversionBean(101L, from, to, quantity, response.getConversionMultiple(), total,
				response.getPort());
	}
	
	
	@GetMapping("/currency-conversion-feign/{from}/to/{to}/quantity/{quantity}") 
	public ConversionBean conversionFnFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		ConversionBean response = proxy.exchangeValue(from, to);
		BigDecimal total = quantity.multiply(response.getConversionMultiple()); // big decimal multiply like this
		return new ConversionBean(101L, from, to, quantity, response.getConversionMultiple(), total,
				response.getPort());
	}
}
