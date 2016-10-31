package com.example;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Entity
@Access(AccessType.FIELD)
@Table(name="PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    Integer id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(column = @Column(name = "PRICE_AMOUNT", scale = 4, precision = 17), name = "money.amount"),
        @AttributeOverride(column = @Column(name = "PRICE_CURRENCY", length = 10), name = "money.currency")
    })
    Money price;

    CurrencyUnit currency;

    public Product() {} // JPA

    public Product(Money price) {
        this();
        this.price = price;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Money getPrice() {
        return price;
    }
    public void setPrice(Money price) {
        this.price = price;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyUnit currency) {
        this.currency = currency;
    }

}
