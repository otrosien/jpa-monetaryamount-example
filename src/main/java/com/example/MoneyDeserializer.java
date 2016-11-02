package com.example;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class MoneyDeserializer extends JsonDeserializer<BigMoney> {

    @Override
    public BigMoney deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        BigDecimal amount = null;
        CurrencyUnit currency = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {

            parser.nextToken();
            final String field = parser.getCurrentName();

            if (field.equals("amount")) {
                amount = parser.getDecimalValue();
            } else if (field.equals("currency")) {
                currency = context.readValue(parser, CurrencyUnit.class);
            } else if (context.isEnabled(FAIL_ON_UNKNOWN_PROPERTIES)) {
                throw UnrecognizedPropertyException.from(parser, Money.class, field,
                        Arrays.<Object>asList("amount", "currency"));
            } else {
                parser.skipChildren();
            }
        }

        if(amount == null || currency == null) {
            return null;
        }

        return BigMoney.of(currency, amount);
    }

}
