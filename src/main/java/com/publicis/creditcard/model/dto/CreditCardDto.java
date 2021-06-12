package com.publicis.creditcard.model.dto;

import com.publicis.creditcard.validation.CardNumberConstraint;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class CreditCardDto {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @CardNumberConstraint
    @Column(unique = true)
    private String number;

    private BigDecimal balance;

    private BigDecimal cardLimit;


    public CreditCardDto() {
        super();
    }

    public CreditCardDto(String name, String number, BigDecimal balance, BigDecimal limit) {
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.cardLimit = limit;
    }

    public CreditCardDto(String name, String number, BigDecimal limit) {
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
}
