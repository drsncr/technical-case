package com.op.technicalcase.controller;

import com.op.technicalcase.model.*;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/conversion")
@Api(tags={"Currency conversion api list"})
@ApiResponses(value = {
        @ApiResponse(code=200, message = "OK - successfully retrieved"),
        @ApiResponse(code=201, message = "CREATED - transaction created"),
        @ApiResponse(code=400, message = "BAD_REQUEST - invalid parameters"),
        @ApiResponse(code=404, message = "NOT_FOUND - content not found")
})
public class CurrencyConversionController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CurrencyConversionService conversionService;

    @GetMapping(value="/exchange-rate/{sourceCurrency}/{targetCurrency}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets the exchange of source currency and target currency pair")
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable Currency sourceCurrency, @PathVariable Currency targetCurrency){
        BigDecimal exchangeRate = exchangeRateService.getRateFromApi(sourceCurrency, targetCurrency);
        return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
    }

    @PostMapping(value="/target", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Converts given amount in certain currency to target currency")
    public ResponseEntity<ConversionOutput> convertToTargetCurrency(@RequestBody @Valid ConversionInput conversionInput){
        ConversionOutput conversionOutput = conversionService.convertToTargetCurrency(conversionInput);
        return new ResponseEntity<>(conversionOutput, HttpStatus.CREATED);
    }

    @PostMapping(value="/list", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets the list of filtered conversions via id and creation date")
    public ResponseEntity<List<Conversion>> getConversionList(@RequestBody ConversionFilterObject conversionFilterObject,
                                                              @RequestParam(required = false, defaultValue = "0") Integer page,
                                                              @RequestParam(required = false, defaultValue = "5") Integer size){
        List<Conversion> conversionList = conversionService.getConversions(conversionFilterObject, page, size);
        return new ResponseEntity<>(conversionList, HttpStatus.OK);
    }

}
