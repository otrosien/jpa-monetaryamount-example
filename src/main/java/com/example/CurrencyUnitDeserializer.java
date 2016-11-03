package com.example;

import java.io.IOException;

import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

public class CurrencyUnitDeserializer extends ValueInstantiator {

    @Override
    public boolean canCreateFromString() {
        return true;
    }

    @Override
    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
        return CurrencyUnit.of(value);
    }
}
