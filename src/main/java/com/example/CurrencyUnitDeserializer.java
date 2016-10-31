package com.example;

import java.io.IOException;

import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CurrencyUnitDeserializer extends JsonDeserializer<CurrencyUnit> {

    @Override
    public CurrencyUnit deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final String currencyCode = parser.getValueAsString();
        return CurrencyUnit.of(currencyCode);
    }
}
