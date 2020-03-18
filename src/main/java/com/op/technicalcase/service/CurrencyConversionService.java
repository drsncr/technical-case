package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InSufficientQueryParamException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.Conversion;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import com.op.technicalcase.validation.ConversionInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CurrencyConversionService {

    @Autowired
    ConversionInputValidator conversionInputValidator;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CurrencyConversionRepository conversionRepository;

    public ConversionOutput convertToTargetCurrency(ConversionInput conversionInput) {
/*        List<String> exceptionFields = conversionInputValidator.validate(conversionInput);
        if(!exceptionFields.isEmpty())
            throw new InvalidFieldException(exceptionFields.toString());*/

        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(conversionInput.getSourceCurrency(), conversionInput.getTargetCurrency());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

    public List<Conversion> getConversions(Long id, String creationDateString, Integer page, Integer size) {
        if(id == null && creationDateString.equals(null))
            throw new InSufficientQueryParamException();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate creationDate = LocalDate.parse(creationDateString, dateTimeFormatter);

        List<Conversion> conversionList = conversionRepository.getConversionList(id, creationDate);
        return conversionList;
        //Pageable pageable = PageRequest.of(page,size);

    }
}
