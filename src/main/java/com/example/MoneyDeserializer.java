package com.example;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class MoneyDeserializer extends JsonDeserializer<Money> {

    @Override
    public Money deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        BigDecimal amount = null;
        CurrencyUnit currency = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            final String field = parser.getCurrentName();

            parser.nextToken();

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

        Assert.notNull(amount, "amount");
        Assert.notNull(currency, "currency");
        return Money.of(currency, amount);
    }

}
