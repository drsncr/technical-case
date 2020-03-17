package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.Conversion;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import com.op.technicalcase.validation.ConversionInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyConversionService {

    @Autowired
    ConversionInputValidator conversionInputValidator;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CurrencyConversionRepository conversionRepository;

    public ConversionOutput convertToTargetCurrency(ConversionInput conversionInput) throws InvalidFieldException, ExchangeRateNotFoundException {
        List<String> exceptionFields = conversionInputValidator.validate(conversionInput);
        if(!exceptionFields.isEmpty())
            throw new InvalidFieldException(exceptionFields.toString());

        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(conversionInput.getSourceCurrency(), conversionInput.getTargetCurrency());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

}
