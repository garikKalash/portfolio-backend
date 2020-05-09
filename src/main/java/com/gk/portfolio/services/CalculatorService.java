package com.gk.portfolio.services;


import com.gk.portfolio.exceptions.InvalidSimpleExpressionException;
import com.gk.portfolio.validators.ExpressionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CalculatorService {

    private final ExpressionValidator expressionValidator;

    @Autowired
    public CalculatorService(ExpressionValidator expressionValidator) {
        this.expressionValidator = expressionValidator;
    }

    public Integer evaluate(@NotNull String expression) {
        if (!expressionValidator.isValid(expression))
            throw new InvalidSimpleExpressionException("Given expression is invalid for evaluating: " + expression);
        expression = expression.replaceAll(" ", "");
        String[] splitByPlus = expression.split("\\+");
        List<Integer> toSum = new ArrayList<>(splitByPlus.length);
        Arrays.stream(splitByPlus).forEach(s -> toSum.add(multiplyDigitsInString(s)));
        return toSum.stream().mapToInt(Integer::intValue).sum();
    }

    private Integer multiplyDigitsInString(String justMultiplicationValidExpression) {
        try {
            return Arrays.stream(justMultiplicationValidExpression.split("\\*"))
                    .mapToInt(Integer::valueOf)
                    .reduce(1, (left, right) -> left * right);
        } catch (NumberFormatException ex) {
            throw new InvalidSimpleExpressionException("This part of given expression is invalid for evaluating: " + justMultiplicationValidExpression);
        }
    }
}
