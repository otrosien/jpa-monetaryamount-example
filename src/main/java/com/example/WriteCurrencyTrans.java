package com.example;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.foundation.AbstractTransformationMapping;
import org.eclipse.persistence.mappings.transformers.FieldTransformerAdapter;
import org.eclipse.persistence.sessions.Session;

@SuppressWarnings("serial")
public class WriteCurrencyTrans extends FieldTransformerAdapter {

    private AttributeAccessor attributeAccessor;

    @Override
    public void initialize(AbstractTransformationMapping mapping) {
        this.attributeAccessor = mapping.getAttributeAccessor();
    }

    public String buildFieldValue(Object object, String fieldName, Session session) {
        MonetaryAmount amount = (MonetaryAmount) attributeAccessor.getAttributeValueFromObject(object);
        if(amount == null) {
            return null;
        }
        return amount.getCurrency().getCurrencyCode();
    }
}
