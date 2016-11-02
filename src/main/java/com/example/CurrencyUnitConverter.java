package com.example;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.money.CurrencyUnit;

@Converter(autoApply=true)
public class CurrencyUnitConverter implements AttributeConverter<CurrencyUnit, String> {
    @Override
    public String convertToDatabaseColumn(CurrencyUnit attribute) {
        return Optional.ofNullable(attribute).map(CurrencyUnit::getCode).orElse(null);
    }

    @Override
    public CurrencyUnit convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData).map(CurrencyUnit::of).orElse(null);
    }

}
