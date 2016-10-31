package com.example;

import java.io.IOException;

import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CurrencyUnitSerializer extends JsonSerializer<CurrencyUnit> {

    @Override
    public void serialize(CurrencyUnit value, JsonGenerator generator, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        generator.writeString(value.getCurrencyCode());
    }

}
