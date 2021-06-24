package com.publicis.creditcard.repository;

import com.publicis.creditcard.model.CreditCard;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CreditCardRepositoryIT {

    @Autowired
    ICreditCardRepository creditCardRepository;

    @Test
    public void testSuccessfulCreditCardSaveOperation() {
        CreditCard actualEntity = creditCardRepository.save(new CreditCard("Alice", "1111 2222 3333 4444", BigDecimal.valueOf(557), BigDecimal.valueOf(1000)));
        Assertions.assertTrue(Matchers.notNullValue().matches(actualEntity), "Project save successfully.");
        creditCardRepository.deleteAll();
    }

    @Test()
    public void testUniqueConstraintViolationOnColumnNumber() {
        assertThrows(Exception.class, () -> {
            creditCardRepository.save(new CreditCard("Alice", "1111 2222 3333 4444", BigDecimal.valueOf(557), BigDecimal.valueOf(1000)));
            creditCardRepository.save(new CreditCard("Alice", "1111 2222 3333 4444", BigDecimal.valueOf(557), BigDecimal.valueOf(1000)));
        });
    }

    @Test
    public void testFindPage() {
        //given
        CreditCard card1 = new CreditCard("Alec", "5555 2222 3333 4448", BigDecimal.valueOf(55700, 2), BigDecimal.valueOf(100000, 2));
        CreditCard card2 = new CreditCard("Boris", "5555 7722 3333 4442", BigDecimal.valueOf(-1200, 2), BigDecimal.valueOf(40000,2));
        CreditCard card3 = new CreditCard("Mahatma", "8866 2222 3333 44434", BigDecimal.valueOf(2000, 2), BigDecimal.valueOf(0, 2));
        CreditCard card4 = new CreditCard("Ziggy", "8866 2211 3333 44437", BigDecimal.valueOf(-1034, 2), BigDecimal.valueOf(777700, 2));

        Collection<CreditCard> cards = Arrays.asList(card4, card3, card2, card1);
        cards.stream().map(card -> creditCardRepository.save(card)).collect(Collectors.toList());
        // when
        Iterable<CreditCard> all = creditCardRepository.findAll(Sort.by("name"));
        //then
        List<CreditCard> actual = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        List<CreditCard> expected = Arrays.asList(card1, card2, card3, card4);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testFindPageWhenThereZeroElements() {
        //given
        creditCardRepository.deleteAll();
        // when
        Iterable<CreditCard> all = creditCardRepository.findAll(Sort.by("name"));
        //then
        List<CreditCard> actual = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        Assertions.assertEquals(actual.size(), 0);
    }
}