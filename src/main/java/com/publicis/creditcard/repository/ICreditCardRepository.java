package com.publicis.creditcard.repository;

import com.publicis.creditcard.model.dto.CreditCardDto;
import org.springframework.data.repository.CrudRepository;

public interface ICreditCardRepository extends CrudRepository<CreditCardDto, Long> {

}
