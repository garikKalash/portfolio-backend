package com.gk.portfolio.controllers;

import com.gk.portfolio.services.DataFixerCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {
    @Autowired
    DataFixerCurrencyService dataFixerCurrencyService;



    @GetMapping("/from/{from}/to/{to}")
    public String getRates(@PathVariable("from") String from,
                           @PathVariable("to") String to){
        return dataFixerCurrencyService.exchange(from, to);
    }
}
