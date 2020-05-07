package com.gk.portfolio.services

import com.gk.portfolio.exceptions.InvalidSimpleExpressionException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class CalculatorServiceUSpec extends Specification {
    @Autowired CalculatorService calculatorService

    def "evaluate valid exp"() {
        given:
        String exp = '5+5'

        when:
        def result  = calculatorService.evaluate(exp)

        then:
        result == 10
    }

    def "evaluate invalid exp"() {
        given:
        String exp = '5+5+'

        when:
        calculatorService.evaluate(exp)

        then:
        thrown(InvalidSimpleExpressionException)
    }
}
