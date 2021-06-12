package com.publicis.creditcard.http.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.publicis.creditcard.model.CreditCard;
import com.publicis.creditcard.service.ICreditCardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CreditCardController.class)
class CreditCardControllerTest {

    private static ObjectWriter writer;
    private static ObjectWriter collectionWriter;

    @BeforeAll
    public static void mapperSetup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        writer = mapper.writerFor(CreditCard.class);
        collectionWriter = mapper.writerFor(Collection.class);

    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICreditCardService creditCardService;

    @Test
    public void testResponseIsCreatedWhenServiceCreateIsSuccess() throws Exception {
        CreditCard obj = new CreditCard("alice", "1111 2222 3333 4451", BigDecimal.valueOf(5000L));
        doReturn(obj).when(creditCardService).create(Mockito.any());
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(writer.writeValueAsString(obj)));

    }

    @Test
    public void testResponseIsConflictWhenServiceThrowsDataAccessException() throws Exception {
        doThrow(DataIntegrityViolationException.class).when(creditCardService).create(Mockito.any());
        CreditCard obj = new CreditCard("alice", "1111 2222 3333 4451", BigDecimal.valueOf(1000L), BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$", equalTo("A credit card with the same number already exits in the system.")));

    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberExceedMaximumNumberOfDigits() throws Exception {
        CreditCard obj = new CreditCard("alice", "1111 2222 333 4444 5555 6666", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberIsNotNumericOnly() throws Exception {
        CreditCard obj = new CreditCard("alice", "1111 -a*$ 333 4444", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberIsNotLunhSequence() throws Exception {
        CreditCard obj = new CreditCard("alice", "1111 2222 3333 4457", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    @Test
    public void testResponseIsOkWhenServiceRetrievesCreditCardsSuccessfully() throws Exception {
        CreditCard cardAlice = new CreditCard("Alice", "1111 2222 3333 4457", BigDecimal.valueOf(1000L), BigDecimal.valueOf(5000L));
        CreditCard cardBob = new CreditCard("Bob", "555 6666 7777 8888", BigDecimal.valueOf(-54), BigDecimal.valueOf(1000L));

        List<CreditCard> cards = Arrays.asList(cardAlice, cardBob);
        doReturn(cards).when(creditCardService).listCreditCards();
        mockMvc.perform(get("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(collectionWriter.writeValueAsString(cards)));

    }

    private String toJsonAsString(CreditCard dto) throws JsonProcessingException {
        return writer.writeValueAsString(dto);
    }

}