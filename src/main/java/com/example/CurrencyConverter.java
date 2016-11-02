package com.example;

import java.util.Currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {

    @Override
    public String convertToDatabaseColumn(Currency attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCurrencyCode();
    }

    @Override
    public Currency convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Currency.getInstance(dbData);
    }
}
