package com.publicis.creditcard.service;

import com.publicis.creditcard.model.CreditCard;
import com.publicis.creditcard.repository.ICreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CreditCardService implements ICreditCardService{

    @Autowired
    private ICreditCardRepository creditCardRepository;

    @Override
    public CreditCard create(CreditCard creditCardDto) {
        return creditCardRepository.save(creditCardDto);
    }

    @Override
    public Collection<CreditCard> listCreditCards() {
        return null;
    }
}
