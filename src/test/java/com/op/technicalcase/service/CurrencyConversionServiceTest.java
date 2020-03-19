package com.op.technicalcase.service;

import com.op.technicalcase.exception.InSufficientQueryParamException;
import com.op.technicalcase.exception.InvalidConversionInputException;
import com.op.technicalcase.exception.InvalidPageNumberException;
import com.op.technicalcase.exception.InvalidSizeNumberException;
import com.op.technicalcase.model.Conversion;
import com.op.technicalcase.model.ConversionFilterObject;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.model.ConversionOutput;
import com.op.technicalcase.repository.CurrencyConversionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CurrencyConversionServiceTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private CurrencyConversionRepository currencyConversionRepository;

    @InjectMocks
    CurrencyConversionService currencyConversionService;

    ConversionInput conversionInput;
    BigDecimal exchangeRate;
    Conversion conversion;

    ConversionFilterObject conversionFilterObject;
    List<Conversion> conversionList;

    @BeforeEach
    public void init(){
        conversionInput = new ConversionInput();
        conversionInput.setSourceAmount(BigDecimal.TEN);
        conversionInput.setSourceCurrency(Currency.getInstance("USD"));
        conversionInput.setTargetCurrency(Currency.getInstance("TRY"));
        exchangeRate = BigDecimal.valueOf(2);
        conversion = new Conversion(conversionInput);

        conversionFilterObject = new ConversionFilterObject();
        conversionFilterObject.setId(1l);
        conversionFilterObject.setCreationDate("19-03-2020");

        conversionList = new ArrayList<>();
        conversionList.add(conversion);
    }

    @Test
    public void convertToTargetCurrency_whenConversionInputIsNull_throwsConversionInputInvalidException(){
        conversionInput = null;
        Assertions.assertThrows(InvalidConversionInputException.class, () -> {
            ConversionOutput conversionOutput = currencyConversionService.convertToTargetCurrency(conversionInput);
        });
    }

    @Test
    public void convertToTargetCurrency_whenOneOfConversionInputAttributeIsNull_throwsConversionInputInvalidException(){
        ConversionInput newConversionInput = conversionInput;
        newConversionInput.setSourceAmount(null);
        Assertions.assertThrows(InvalidConversionInputException.class, () -> {
            ConversionOutput conversionOutput = currencyConversionService.convertToTargetCurrency(conversionInput);
        });

        newConversionInput = conversionInput;
        newConversionInput.setSourceCurrency(null);
        Assertions.assertThrows(InvalidConversionInputException.class, () -> {
            ConversionOutput conversionOutput = currencyConversionService.convertToTargetCurrency(conversionInput);
        });

        newConversionInput = conversionInput;
        newConversionInput.setTargetCurrency(null);
        Assertions.assertThrows(InvalidConversionInputException.class, () -> {
            ConversionOutput conversionOutput = currencyConversionService.convertToTargetCurrency(conversionInput);
        });
    }

    @Test
    public void convertToTargetCurrency_whenValidConversionInput_returnActualValue(){
        Mockito.when(exchangeRateService.getRateFromApi(Mockito.any(Currency.class), Mockito.any(Currency.class)))
               .thenReturn(exchangeRate);

        conversion.setTargetAmount(conversionInput.getSourceAmount().multiply(exchangeRate));
        Mockito.when(currencyConversionRepository.save(Mockito.any(Conversion.class))).thenReturn(conversion);

        ConversionOutput conversionOutput = currencyConversionService.convertToTargetCurrency(conversionInput);
        Assertions.assertEquals(conversionOutput.getTargetAmount(), BigDecimal.valueOf(20));
    }


    @Test
    public void getConversions_whenConversionFilterObjectIsNull_throwsInSufficientQueryParamException(){
        conversionFilterObject = null;
        Assertions.assertThrows(InSufficientQueryParamException.class, () -> {
            List<Conversion> conversionList = currencyConversionService.getConversions(conversionFilterObject, 0, 5);
        });
    }

    @Test
    public void getConversions_whenIdAndCreationDateIsNull_throwsInSufficientQueryParamException(){
        conversionFilterObject.setId(null);
        conversionFilterObject.setCreationDate(null);
        Assertions.assertThrows(InSufficientQueryParamException.class, () -> {
            List<Conversion> conversionList = currencyConversionService.getConversions(conversionFilterObject, 0, 5);
        });
    }

    @Test
    public void getConversions_whenPageNumberIsInvalid_throwsInvalidPageNumberException(){
        Assertions.assertThrows(InvalidPageNumberException.class, () -> {
            List<Conversion> conversionList = currencyConversionService.getConversions(conversionFilterObject, -1, 5);
        });
    }

    @Test
    public void getConversions_whenSizeNumberIsInvalid_throwsInvalidPageNumberException(){
        Assertions.assertThrows(InvalidSizeNumberException.class, () -> {
            List<Conversion> conversionList = currencyConversionService.getConversions(conversionFilterObject, 0, 0);
        });
    }

    @Test
    public void getConversions_whenCreationDateIsInvalid_throwsDateTimeParseException(){
        conversionFilterObject.setCreationDate("2020.03.19");
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            List<Conversion> conversionList = currencyConversionService.getConversions(conversionFilterObject, 0, 5);
        });
    }

    @Test
    public void getConversions_whenParametersAreValid_returnActualValue(){
        Mockito.when(currencyConversionRepository.getConversionList(Mockito.any(Long.class),
                Mockito.any(LocalDate.class),
                Mockito.any(Pageable.class))).thenReturn(conversionList);

        List<Conversion> conversions = currencyConversionService.getConversions(conversionFilterObject, 0, 5);
        Assertions.assertEquals(conversions.size(), 1);
    }
}
