package com.example;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.mappings.transformers.FieldTransformerAdapter;
import org.eclipse.persistence.sessions.Session;

@SuppressWarnings("serial")
public class WriteAmountTrans extends FieldTransformerAdapter {

    public BigDecimal buildFieldValue(Object object, String fieldName, Session session) {
        Product product = (Product) object;
        MonetaryAmount amount = product.getPrice();
        if(amount == null) {
            return null;
        }
        return amount.getNumber().numberValue(BigDecimal.class);
    }

}
