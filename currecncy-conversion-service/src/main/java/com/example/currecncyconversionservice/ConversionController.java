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
	
	@Autowired
	private RestTemplate restTemplateloc;
	
	@GetMapping("/currency-conversion-restobj/{from}/to/{to}/quantity/{quantity}")
	public ConversionBean conversionRestobjFn(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> uriVarible = new HashMap<>();
		uriVarible.put("from", from);
		uriVarible.put("to", to);
		// url same as http://localhost:8000/exchange-currency/{from}/to/{to}
		// just name replaced exchange-service by localhost:8000
		ConversionBean response = restTemplateloc.getForObject("http://exchange-service/exchange-currency/{from}/to/{to}", ConversionBean.class, uriVarible);
		BigDecimal total = quantity.multiply(response.getConversionMultiple()); // big decimal multiply like this
		return new ConversionBean(101L, from, to, quantity, response.getConversionMultiple(), total,
				response.getPort());
	}
	
}
