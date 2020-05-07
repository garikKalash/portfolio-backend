package com.gk.portfolio.controllers;

import com.gk.portfolio.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorController {

    @Autowired
    private CalculatorService simpleCalculator;

    @GetMapping(value = "/{expression}/result")
    public ResponseEntity<Integer> getResult(@PathVariable String expression){
        return new ResponseEntity<>(simpleCalculator.evaluate(expression), HttpStatus.OK);
    }

}