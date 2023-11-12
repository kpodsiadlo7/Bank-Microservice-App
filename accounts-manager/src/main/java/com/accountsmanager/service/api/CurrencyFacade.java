package com.accountsmanager.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@RequiredArgsConstructor
public class CurrencyFacade {
    private final FeignServiceExchangeCurrency feignServiceExchangeCurrency;

    public BigDecimal exchangeCurrency(String currencyAccountIncrease, String currencyAccountToDecrease, BigDecimal amount) {
        CurrencyApi currencyApi = feignServiceExchangeCurrency.getRate(currencyAccountToDecrease, currencyAccountIncrease);
        MathContext mc = new MathContext(4);
        BigDecimal rate = BigDecimal.valueOf(currencyApi.getCurrency(currencyAccountIncrease));
        return rate.multiply(amount, mc);
    }
}
