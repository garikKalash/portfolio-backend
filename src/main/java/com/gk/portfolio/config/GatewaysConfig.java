package com.gk.portfolio.config;

import com.gk.portfolio.gateways.CurrencyGateway;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewaysConfig {
    @Bean
    public CurrencyGateway currencyGateway(){
        return JAXRSClientFactory.create(CurrencyGateway.URL, CurrencyGateway.class);
    }
}

