package com.example;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import java.util.Locale;

import org.joda.money.CurrencyUnit;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.module.SimpleValueInstantiators;

@Configuration
public class JacksonCustomization implements Jackson2ObjectMapperBuilderCustomizer {

    @Bean
    public MoneyModule moneyModule() {
        return new MoneyModule();
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
            jacksonObjectMapperBuilder //
            .locale(Locale.US) //
            .timeZone("UTC") //
            .indentOutput(true) //
            .serializationInclusion(Include.ALWAYS) //
            .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS, FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @SuppressWarnings("serial")
    static class MoneyModule extends SimpleModule {

        @Override
        public void setupModule(SetupContext context) {

            final SimpleSerializers serializers = new SimpleSerializers();
            serializers.addSerializer(CurrencyUnit.class, new CurrencyUnitSerializer());
            context.addSerializers(serializers);

            final SimpleValueInstantiators instantiators = new SimpleValueInstantiators();
            instantiators.addValueInstantiator(CurrencyUnit.class, new CurrencyUnitDeserializer());
            context.addValueInstantiators(instantiators);

        }
    }
}
