package com.publicis.creditcard.service;

import com.publicis.creditcard.model.CreditCard;

import java.util.Collection;

public interface ICreditCardService {

    CreditCard create(CreditCard creditCardDto);
    Collection<CreditCard> listCreditCards();
}
