package com.publicis.creditcard.service;

import com.publicis.creditcard.model.CreditCard;
import com.publicis.creditcard.repository.ICreditCardRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class CreditCardServiceTest {

    @MockBean
    private ICreditCardRepository creditCardRepository;

    @Autowired
    private ICreditCardService instanceUnderTest;

    @Test
    public void testSortingByName() {
        List<CreditCard> result = Arrays.asList(new CreditCard("Mahatma", "8866 2222 3333 44434", BigDecimal.valueOf(2000, 2), BigDecimal.valueOf(0, 2)));
        Mockito.doReturn(result).when(creditCardRepository).findAll(Sort.by("name"));

        Collection<CreditCard> listCreditCards = instanceUnderTest.listCreditCards();
        Assert.assertEquals(result, listCreditCards);
    }
}