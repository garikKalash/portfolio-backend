package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.exceptions.InvalidSimpleExpressionException
import com.gk.portfolio.validators.ExpressionValidator
import spock.lang.Specification


class CalculatorServiceUTest extends Specification {
    ExpressionValidator expressionValidator = Mock(ExpressionValidator)
    CalculatorService calculatorService = new CalculatorService(expressionValidator)


    def "test evaluate "() {
        given:
        def validExp = "5+5*6"
        def invalidExp = "+++"

        when:
        def result = calculatorService.evaluate(validExp)

        then:
        expressionValidator.isValid(_) >> true
        result == 35

        when:
        calculatorService.evaluate(invalidExp)

        then:
        expressionValidator.isValid(_) >> false
        thrown(InvalidSimpleExpressionException)

    }
}
