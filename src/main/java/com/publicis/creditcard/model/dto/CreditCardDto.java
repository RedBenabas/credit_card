package com.publicis.creditcard.model.dto;

import com.publicis.creditcard.validation.CardNumberConstraint;

import java.math.BigDecimal;

public class CreditCardDto {
    private String name;

    @CardNumberConstraint
    private String number;

    private BigDecimal balance;

    private BigDecimal limit;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
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

    public BigDecimal getLimit() {
        return limit;
    }

    public CreditCardDto(String name, String number, BigDecimal balance, BigDecimal limit) {
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.limit = limit;
    }

}
