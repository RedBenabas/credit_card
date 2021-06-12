package com.publicis.creditcard.repository;

import com.publicis.creditcard.model.dto.CreditCardDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CreditCardRepositoryIT {

    @Autowired
    ICreditCardRepository creditCardRepository;

    private CreditCardDto creditCardDto = new  CreditCardDto("Alice", "1111 2222 3333 4444", BigDecimal.valueOf(557), BigDecimal.valueOf(1000));

    @Test
    public void testSuccessfulCreditCardSaveOperation() {
        CreditCardDto actualEntity = creditCardRepository.save(creditCardDto);
        assertTrue("Project save failed.", Matchers.notNullValue().matches(actualEntity));
    }

    @Test
    public void testUniqueConstraintViolationOnColumnNumber() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            creditCardRepository.save(creditCardDto);
        });
    }
}