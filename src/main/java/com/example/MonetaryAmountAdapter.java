package com.example;

import java.math.BigDecimal;
import java.util.List;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.internal.descriptors.FieldTransformation;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.foundation.AbstractTransformationMapping;
import org.eclipse.persistence.mappings.transformers.AttributeTransformerAdapter;
import org.eclipse.persistence.mappings.transformers.FieldTransformerAdapter;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;
import org.javamoney.moneta.Money;

public class MonetaryAmountAdapter {

    @SuppressWarnings("serial")
    public static class ReadTransformer extends AttributeTransformerAdapter {

        private List<FieldTransformation> fieldTransformations;

        @Override
        public void initialize(AbstractTransformationMapping mapping) {
            this.fieldTransformations = mapping.getFieldTransformations();
            super.initialize(mapping);
        }

        @Override
        public MonetaryAmount buildAttributeValue(Record record, Object object, Session session) {
            Object[] values = {
                    record.get(fieldTransformations.get(0).getFieldName()),
                    record.get(fieldTransformations.get(1).getFieldName())
            };
            if(values[0] == null || values[1] == null) {
                return null;
            }
            if(values[0] instanceof Number) {
                return Money.of((Number)values[0], (String)values[1]);
            } else {
                return Money.of((Number)values[1], (String)values[0]);
            }
        }

    }

    @SuppressWarnings("serial")
    public static class WriteAmountTransformer extends FieldTransformerAdapter {

        private AttributeAccessor attributeAccessor;

        @Override
        public void initialize(AbstractTransformationMapping mapping) {
            this.attributeAccessor = mapping.getAttributeAccessor();
        }

        public BigDecimal buildFieldValue(Object object, String fieldName, Session session) {
            MonetaryAmount amount = (MonetaryAmount) attributeAccessor.getAttributeValueFromObject(object);
            if (amount == null) {
                return null;
            }
            return amount.getNumber().numberValue(BigDecimal.class);
        }

    }

    @SuppressWarnings("serial")
    public static class WriteCurrencyTransformer extends FieldTransformerAdapter {

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

}
