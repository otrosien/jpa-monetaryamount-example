package com.example;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.foundation.AbstractTransformationMapping;
import org.eclipse.persistence.mappings.transformers.AttributeTransformerAdapter;
import org.eclipse.persistence.mappings.transformers.FieldTransformerAdapter;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;
import org.javamoney.moneta.Money;

public class MonetaryAmountAdapter {

    public static class ReadTransformer extends AttributeTransformerAdapter {

        @Override
        public MonetaryAmount buildAttributeValue(Record record, Object object, Session session) {
            Number amount = (Number) record.get("AMOUNT");
            String currency = (String) record.get("CURRENCY");
            if (amount == null || currency == null) {
                return null;
            }
            return Money.of(amount, currency);
        }

    }

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
