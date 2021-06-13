package com.publicis.creditcard.service;

import com.publicis.creditcard.model.CreditCard;
import com.publicis.creditcard.repository.ICreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        Iterable<CreditCard> all = creditCardRepository.findAll(Sort.by("name"));
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());

    }
}
