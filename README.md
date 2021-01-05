## Microservice example by in28minuts 

Reference : https://github.com/in28minutes/spring-microservices/tree/master/03.microservices


Implemented : web rest api, actuator, Zuul for api gateway, devtools for hot reload, git for dynamic app config, zipkin and salute for monitor, eureka server, JPA, h2 data base- munual sql query in exchange data base;


Did’t implement : spring bus- for instance git config updater, hybrid dash board - for tracing api performance.

#— create a git-repo project
$ git init
Add as many application config file with exact name

# create a config server which’ll connect git config to micro services

# In pom.xml

<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

# In application.properties

spring.application.name = spring-cloud-config-server
server.port = 8888

# to test on chrome : http://localhost:8888/limits-service/default
# to link git to spring cloud server
spring.cloud.config.server.git.uri = file:///Users/amit/Desktop/SBProj/microserviceTest/git-localfolder

In app.java
@EnableConfigServer

# to test : http://localhost:8888/limits-service/default

#— create a limits-service - returns max and min var declare in git

# In pom.xml

spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client

# In bootsrap.properties

spring.application.name = limits-service
server.port = 8080

# rename to bootstrap.properties
# link to spring cloud server
spring.cloud.config.uri = http://localhost:8888
# means full url would be http://localhost:8888/qa
spring.profiles.active = qa

# to test : http://localhost:8080/limits


#— create a exchange service - returns currency rate, save exchange rate in db could be multiple service

# In pom.xml

spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-sleuth
spring-cloud-sleuth-zipkin
spring-boot-starter-data-jpa
H2

# In application.properties

spring.application.name = exchange-service
server.port = 8000

spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.show-sql = true
spring.h2.console.enabled = true

eureka.client.service-url.default-zone=http://localhost:8761/eureka

spring.zipkin.base-url= http://127.0.0.1:9411/

# in data.sql

insert into exchange_currency(id,currency_from,currency_to,conversion_multiple,port)
values(1001,'EUR','INR',65,0);
insert into exchange_currency(id,currency_from,currency_to,conversion_multiple,port)
values(1002,'AUD','INR',25,0);
insert into exchange_currency(id,currency_from,currency_to,conversion_multiple,port)
values(1003,'US','INR',75,0);


# to Test : http://localhost:8000/exchange-currency/EUR/to/INR

#— create a conversion service which call to exchange service using rest template and return calculated result

# In pom.xml

spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-sleuth
spring-cloud-sleuth-zipkin

# in application.properties

spring.application.name = currency-conversion-service
server.port = 8100
#eureka.client.service-url.default-zone=http://localhost:8761/eureka

spring.zipkin.base-url= http://127.0.0.1:9411/

# in application.java

// for load blancing 

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

# in exchange-controller - for call exchange api

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

# to test : http://localhost:8100/currency-conversion-restobj/US/to/INR/quantity/101


#— create a eureka server to check up services and give access of available service to another service

# In pom.xml

spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-server

# in application.properties
spring.application.name=netflix-eureka-naming-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false


# app.java

@EnableEurekaServer

# to test : http://localhost:8761/

#— create a api gateway to redirect all api call and easy to monitor at one place 

# In pom.xml
spring-boot-starter-actuator
spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-netflix-zuul

# in application.properties

spring.application.name = netflix-zuul-api-gatewat-server
server.port = 8765
eureka.client.service-url.default-zone=http://localhost:8761/eureka

# in app.java

@EnableZuulProxy


# create a filter named ZuulLoggingFilter

package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {},request -> {} ",request, request.getRequestURI());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}


# to test : 

Example :: http://localhost:8765/{servicename}/{normalurl}

http://localhost:8765/currency-conversion-service/currency-conversion-restobj/US/to/INR/quantity/101


# start zipping using download jar file with java cli

java -jar zipkin-server-2.23.2-exec.jar

http://127.0.0.1:9411/

Choose service type  and search it

    
