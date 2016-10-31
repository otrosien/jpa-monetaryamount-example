package com.example;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment=NONE)
public class DemoApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    private Product p1 = new Product(Money.of(CurrencyUnit.EUR, new BigDecimal("1.23")));

    @Before
    public void prepare() {
        productRepository.save(p1);
    }

    @Test
    public void check() {
        Product p2 = productRepository.findOne(p1.getId());
        assertEquals(p1.getPrice(), p2.getPrice());
    }

}
