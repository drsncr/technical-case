package com.op.technicalcase.service;


import com.op.technicalcase.constant.ErrorMessage;
import com.op.technicalcase.exception.*;
import com.op.technicalcase.model.*;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        if(conversionInput == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "conversionInput");
        if(conversionInput.getSourceAmount() == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "sourceAmount");
        if(conversionInput.getSourceCurrency() == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "sourceCurrency");
        if(conversionInput.getTargetCurrency() == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "targetCurrency");

        BigDecimal exchangeRate = exchangeRateService.getExchangeRate(conversionInput.getSourceCurrency(), conversionInput.getTargetCurrency());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

    public PageableConversionListObject getConversions(ConversionFilterObject conversionFilterObject, Integer page, Integer size) {
        if(conversionFilterObject == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "conversionFilterObject");
        if(conversionFilterObject.getId() == null && conversionFilterObject.getCreationDate() == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_CONVERSION_FILTER_OBJECT_MESSAGE, "Both id and creationDate");
        if(page < 0)
            throw new InvalidParameterException(ErrorMessage.INVALID_PAGE_PARAMETER_MESSAGE, "0");
        if(size < 1)
            throw new InvalidParameterException(ErrorMessage.INVALID_SIZE_PARAMETER_MESSAGE, "1");

        LocalDate creationDate = null;
        if(conversionFilterObject.getCreationDate() != null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            creationDate = LocalDate.parse(conversionFilterObject.getCreationDate(), dateTimeFormatter);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("id")));
        Page<Conversion> conversionList = conversionRepository.getConversionList(conversionFilterObject.getId(), creationDate, pageable);
        PageableConversionListObject pageableConversionListObject = new PageableConversionListObject(conversionList);
        return pageableConversionListObject;
    }
}
