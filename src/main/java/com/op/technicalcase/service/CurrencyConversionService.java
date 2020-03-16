package com.op.technicalcase.service;

import com.op.technicalcase.exception.InvalidConversionInputException;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.validation.ConversionInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyConversionService {

    @Autowired
    ConversionInputValidator conversionInputValidator;

    public void convertToTargetCurrency(ConversionInput conversionInput) throws InvalidConversionInputException {
        List<String> exceptionFields = conversionInputValidator.validate(conversionInput);
        if(!exceptionFields.isEmpty())
            throw new InvalidConversionInputException(exceptionFields.toString());
    }

}
