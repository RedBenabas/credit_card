package com.publicis.creditcard.repository;

import com.publicis.creditcard.model.CreditCard;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {

}
