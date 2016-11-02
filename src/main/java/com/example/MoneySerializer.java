package com.example;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MoneySerializer extends StdSerializer<BigMoney> {

    private static final long serialVersionUID = 5466920296453602143L;

    public MoneySerializer() {
        super(BigMoney.class);
    }

    @Override
    public void serialize(final BigMoney value, final JsonGenerator generator, final SerializerProvider provider)
            throws IOException {

        final BigDecimal amount = value.getAmount();
        final CurrencyUnit currency = value.getCurrencyUnit();

        generator.writeStartObject();
        generator.writeNumberField("amount", amount);
        generator.writeObjectField("currency", currency);
        generator.writeEndObject();
    }

}