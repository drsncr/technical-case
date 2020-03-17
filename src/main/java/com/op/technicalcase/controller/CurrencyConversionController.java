package com.op.technicalcase.controller;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.model.ExchangeRate;
import com.op.technicalcase.service.CurrencyConversionService;
import com.op.technicalcase.service.ExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/conversion")
@Api(tags={"Currency conversion api list"})
@ApiResponses(value = {
        @ApiResponse(code=200, message = "OK - successfully retrieved"),
        @ApiResponse(code=201, message = "CREATED - transaction created"),
        @ApiResponse(code=400, message = "BAD_REQUEST - invalid parameters"),
        @ApiResponse(code=404, message = "NO_CONTENT - content not found")
})
public class CurrencyConversionController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CurrencyConversionService conversionService;

    @GetMapping(value="/exchange-rate/{sourceCurrency}/{targetCurrency}", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets the exchange of source currency and target currency pair")
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable String sourceCurrency, @PathVariable String targetCurrency) throws InvalidFieldException, ExchangeRateNotFoundException{
        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(sourceCurrency, targetCurrency);
        return new ResponseEntity<>(exchangeRate, HttpStatus.CREATED);
    }

    @PostMapping(value="/target", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Converts given amount in certain currency to target currency")
    public ResponseEntity<ConversionOutput> convertToTargetCurrency(@RequestBody ConversionInput conversionInput) throws InvalidFieldException, ExchangeRateNotFoundException {
        ConversionOutput conversionOutput = conversionService.convertToTargetCurrency(conversionInput);
        return new ResponseEntity<>(conversionOutput, HttpStatus.CREATED);
    }


}
