package com.example;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.mappings.transformers.FieldTransformerAdapter;
import org.eclipse.persistence.sessions.Session;

@SuppressWarnings("serial")
public class WriteCurrencyTrans extends FieldTransformerAdapter {
    @Override
    public String buildFieldValue(Object object, String fieldName, Session session) {
        Product product = (Product) object;
        MonetaryAmount amount = product.getPrice();
        if(amount == null) {
            return null;
        }
        return amount.getCurrency().getCurrencyCode();
    }
}
