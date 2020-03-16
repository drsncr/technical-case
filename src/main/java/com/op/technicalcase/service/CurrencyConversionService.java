package com.op.technicalcase.service;

import com.op.technicalcase.exception.InvalidConversionInputException;
import com.op.technicalcase.model.Conversion;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import com.op.technicalcase.validation.ConversionInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyConversionService {

    @Autowired
    ConversionInputValidator conversionInputValidator;

    @Autowired
    CurrencyConversionRepository conversionRepository;

    public ConversionOutput convertToTargetCurrency(ConversionInput conversionInput) throws InvalidConversionInputException {
        List<String> exceptionFields = conversionInputValidator.validate(conversionInput);
        if(!exceptionFields.isEmpty())
            throw new InvalidConversionInputException(exceptionFields.toString());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(new BigDecimal("1000"));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

}
