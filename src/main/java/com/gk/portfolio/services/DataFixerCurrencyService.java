package com.gk.portfolio.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gk.portfolio.exceptions.ExternalServiceUnavailableException;
import com.gk.portfolio.gateways.CurrencyGateway;
import com.gk.portfolio.services.api.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DataFixerCurrencyService implements CurrencyService {

    @Autowired CurrencyGateway currencyGateway;
    @Value("${currency.api.token}") String accessKey;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String exchange(String fromCur, String toCur) {
        // this part is because our plan supports just "EUR" base
        if (fromCur.equals("EUR")) {
            try {
                return currencyGateway.getRates(toCur, fromCur, accessKey);
            } catch (Exception ex) {
                throw new ExternalServiceUnavailableException(ex.getMessage());
            }
        } else {
            try {
                String rateFromCur = currencyGateway.getRates(fromCur, "EUR", accessKey);
                String rateToCur = currencyGateway.getRates(toCur, "EUR", accessKey);
                JsonNode rateFromJson = OBJECT_MAPPER.readTree(rateFromCur);
                JsonNode rateToJson = OBJECT_MAPPER.readTree(rateToCur);
                BigDecimal fromVal = rateFromJson.get("rates").get(fromCur).decimalValue();
                BigDecimal toVal = rateToJson.get("rates").get(toCur).decimalValue();
                BigDecimal finalRate = toVal.divide(fromVal, 8, RoundingMode.HALF_DOWN);
                ObjectNode rateJson = OBJECT_MAPPER.createObjectNode();
                rateJson.put(toCur, finalRate);
                ObjectNode result = OBJECT_MAPPER.createObjectNode();
                result.set("rates", rateJson);
                return result.toString();
            } catch (Exception ex) {
                throw new ExternalServiceUnavailableException(ex.getMessage());
            }
        }
    }

}
