package com.op.technicalcase.validation.base;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountValidator {

    public boolean validate(BigDecimal amount){
        if (amount == null) {
            return false;
        }
        else{
            if(amount.compareTo(BigDecimal.ZERO) < 0){
                return false;
            }
        }
        return true;
    }
}
