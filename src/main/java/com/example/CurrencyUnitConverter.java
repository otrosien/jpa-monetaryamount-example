package com.example;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class CurrencyUnitConverter implements AttributeConverter<CurrencyUnit, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyUnit attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCurrencyCode();
    }

    @Override
    public CurrencyUnit convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Monetary.getCurrency(dbData);
    }
}
