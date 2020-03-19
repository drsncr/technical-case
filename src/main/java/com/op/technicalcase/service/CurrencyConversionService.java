package com.op.technicalcase.service;


import com.op.technicalcase.exception.InvalidConversionInputException;
import com.op.technicalcase.exception.InSufficientQueryParamException;
import com.op.technicalcase.exception.InvalidPageNumberException;
import com.op.technicalcase.exception.InvalidSizeNumberException;
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
        if(conversionInput == null)
            throw new InvalidConversionInputException();
        if(conversionInput.getSourceAmount() == null || conversionInput.getSourceCurrency() == null || conversionInput.getTargetCurrency() == null)
            throw new InvalidConversionInputException();

        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(conversionInput.getSourceCurrency(), conversionInput.getTargetCurrency());

        Conversion conversion = new Conversion(conversionInput);
        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));

        Conversion savedConversion = conversionRepository.save(conversion);
        ConversionOutput conversionOutput = new ConversionOutput(savedConversion);

        return conversionOutput;
    }

    public List<Conversion> getConversions(ConversionFilterObject conversionFilterObject, Integer page, Integer size) {
        if((conversionFilterObject == null) || (conversionFilterObject.getId() == null && conversionFilterObject.getCreationDate() == null))
            throw new InSufficientQueryParamException();
        if(page < 0)
            throw new InvalidPageNumberException();
        if(size < 1)
            throw new InvalidSizeNumberException();

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
