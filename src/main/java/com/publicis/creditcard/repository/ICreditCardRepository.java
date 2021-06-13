package com.publicis.creditcard.repository;

import com.publicis.creditcard.model.CreditCard;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {

}
