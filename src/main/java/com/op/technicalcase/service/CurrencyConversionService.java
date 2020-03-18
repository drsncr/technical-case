package com.op.technicalcase.service;


import com.op.technicalcase.exception.InSufficientQueryParamException;
import com.op.technicalcase.model.Conversion;
import com.op.technicalcase.model.ConversionFilterObject;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CurrencyConversionService {

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CurrencyConversionRepository conversionRepository;

    public ConversionOutput convertToTargetCurrency(ConversionInput conversionInput) {
        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(conversionInput.getSourceCurrency(), conversionInput.getTargetCurrency());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

    public List<Conversion> getConversions(ConversionFilterObject conversionFilterObject, Integer page, Integer size) {
        if(conversionFilterObject.getId() == null && conversionFilterObject.getCreationDate() == null)
            throw new InSufficientQueryParamException();

        LocalDate creationDate = null;
        if(conversionFilterObject.getCreationDate() != null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            creationDate = LocalDate.parse(conversionFilterObject.getCreationDate(), dateTimeFormatter);
        }

        Pageable pageable = PageRequest.of(page,size);
        List<Conversion> conversionList = conversionRepository.getConversionList(conversionFilterObject.getId(), creationDate, pageable);
        return conversionList;
    }
}
