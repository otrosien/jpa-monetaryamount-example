package com.example;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Currency;

import javax.money.MonetaryAmount;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadTransformer;
import org.eclipse.persistence.annotations.Transformation;
import org.eclipse.persistence.annotations.WriteTransformer;
import org.eclipse.persistence.annotations.WriteTransformers;

@Entity
@Access(AccessType.FIELD)
@Table(name="PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;

    @Transformation(fetch=FetchType.EAGER, optional=true)
    @ReadTransformer(transformerClass=MonetaryAmountAdapter.ReadTransformer.class)
    @WriteTransformers({
        @WriteTransformer(column=@Column(name="PRICE_AMOUNT", columnDefinition="NUMERIC(17,4)"), transformerClass=MonetaryAmountAdapter.WriteAmountTransformer.class),
        @WriteTransformer(column=@Column(name="PRICE_CURRENCY"), transformerClass=MonetaryAmountAdapter.WriteCurrencyTransformer.class)
    })
    private MonetaryAmount price;

    // Unfortunately NOT using javax.money.CurrencyUnit, as JSON-Patching would fail.
    private Currency currency;

    public Product() {} // JPA

    public Product(MonetaryAmount price) {
        this();
        this.price = price;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public MonetaryAmount getPrice() {
        return price;
    }
    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
