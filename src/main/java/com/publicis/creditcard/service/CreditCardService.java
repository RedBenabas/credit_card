package com.publicis.creditcard.service;

import com.publicis.creditcard.model.dto.CreditCardDto;
import com.publicis.creditcard.repository.ICreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService implements ICreditCardService{

    @Autowired
    private ICreditCardRepository creditCardRepository;

    @Override
    public CreditCardDto create(CreditCardDto creditCardDto) {
        return creditCardRepository.save(creditCardDto);
    }
}
