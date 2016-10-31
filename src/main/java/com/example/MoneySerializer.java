package com.example;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MoneySerializer extends JsonSerializer<Money> {

    @Override
    public void serialize(final Money value, final JsonGenerator generator, final SerializerProvider provider)
            throws IOException {

        final BigDecimal amount = value.getAmount();
        final CurrencyUnit currency = value.getCurrencyUnit();

        generator.writeStartObject();
        {
            generator.writeNumberField("amount", amount);
            generator.writeObjectField("currency", currency);
        }
        generator.writeEndObject();
    }

}