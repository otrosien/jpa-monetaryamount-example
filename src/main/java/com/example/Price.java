package com.example;

import static javax.persistence.AccessType.FIELD;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.Embeddable;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
@Access(FIELD)
public class Price {

    private BigDecimal amount;

    private CurrencyUnit currency;

    @SuppressWarnings("unused")
    private Price() { /* JPA */ }

    @JsonCreator
    public Price(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") CurrencyUnit currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public Money moneyValue() {
        return BigMoney.of(currency, amount).toMoney();
    }
}
