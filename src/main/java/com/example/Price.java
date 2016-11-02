package com.example;

import static javax.persistence.AccessType.FIELD;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.Embeddable;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;

@Embeddable
@Access(FIELD)
public class Price {

    private BigDecimal amount;

    private CurrencyUnit currency;

    @SuppressWarnings("unused")
    private Price() { /* JPA */ }

    @JsonCreator
    private Price(BigDecimal amount, CurrencyUnit currency) {
        moneyValue(Money.of(currency, amount));
    }

    public Price(Money money) {
        moneyValue(money);
    }

    @JsonGetter
    private BigDecimal getAmount() {
        return amount.stripTrailingZeros();
    }

    @JsonGetter
    private CurrencyUnit getCurrency() {
        return currency;
    }

    private void moneyValue(Money money) {
        this.currency = money.getCurrencyUnit();
        this.amount = money.getAmount();
    }

    public Money moneyValue() {
        return Money.of(currency, amount.stripTrailingZeros());
    }
}
