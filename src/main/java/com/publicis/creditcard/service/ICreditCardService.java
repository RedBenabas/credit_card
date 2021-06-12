package com.publicis.creditcard.service;

import com.publicis.creditcard.model.dto.CreditCardDto;

public interface ICreditCardService {

    void create(CreditCardDto creditCardDto);

    CreditCardDto findById(Long id);
}
