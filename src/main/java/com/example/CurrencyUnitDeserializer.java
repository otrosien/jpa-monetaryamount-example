package com.example;

import java.io.IOException;

import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CurrencyUnitDeserializer extends ValueInstantiator {

    @Override
    public boolean canCreateFromString() {
        return true;
    }
//
//    @Override
//    public CurrencyUnit deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
//        final String currencyCode = parser.getValueAsString();
//        return CurrencyUnit.of(currencyCode);
//    }

    @Override
    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
        return CurrencyUnit.of(value);
    }
}
