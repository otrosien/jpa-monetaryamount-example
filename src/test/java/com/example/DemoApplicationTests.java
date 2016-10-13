package com.example;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    private Product p1;

    @Before
    public void prepare() {
        p1 = productRepository.save(new Product(Money.of(1L, "EUR")));
    }

    @Test
    public void check() {
        Product p2 = productRepository.findOne(p1.getId());
        assertEquals(p1.getPrice(), p2.getPrice());
    }

}
