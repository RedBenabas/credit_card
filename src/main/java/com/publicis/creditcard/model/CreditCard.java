package com.publicis.creditcard.model;

import com.publicis.creditcard.validation.CardNumberConstraint;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @CardNumberConstraint
    @Column(unique = true)
    private String number;

    private BigDecimal balance;

    private BigDecimal cardLimit;


    public CreditCard() {
        super();
    }

    public CreditCard(String name, String number, BigDecimal balance, BigDecimal limit) {
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.cardLimit = limit;
    }

    public CreditCard(String name, String number, BigDecimal limit) {
        this.name = name;
        this.number = number;
        this.cardLimit = limit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return id.equals(that.id) &&
                getName().equals(that.getName()) &&
                getNumber().equals(that.getNumber()) &&
                getBalance().equals(that.getBalance()) &&
                getCardLimit().equals(that.getCardLimit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getNumber(), getBalance(), getCardLimit());
    }
}
