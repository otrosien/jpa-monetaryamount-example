package com.example;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.mappings.transformers.AttributeTransformerAdapter;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;
import org.javamoney.moneta.Money;

public class ReadTrans extends AttributeTransformerAdapter {

    @Override
    public MonetaryAmount buildAttributeValue(Record record, Object object, Session session) {
        Number amount = (Number) record.get("AMOUNT");
        String currency = (String) record.get("CURRENCY");
        if(amount == null || currency == null) {
            return null;
        }
        return Money.of(amount, currency);
    }


}
