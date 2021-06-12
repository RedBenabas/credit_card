package com.publicis.creditcard.http.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.publicis.creditcard.model.dto.CreditCardDto;
import com.publicis.creditcard.service.ICreditCardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CreditCardController.class)
class CreditCardControllerTest {

    private static ObjectWriter writer;

    @BeforeAll
    public static void mapperSetup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        writer = mapper.writerFor(CreditCardDto.class);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICreditCardService creditCardService;

    @Test
    public void testResponseIsCreatedWhenServiceCreateIsSuccess() throws Exception {
        CreditCardDto obj = new CreditCardDto("alice", "1111 2222 3333 4451", BigDecimal.valueOf(5000L));
        doReturn(obj).when(creditCardService).create(Mockito.any());
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(writer.writeValueAsString(obj)));

    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberExceedMaximumNumberOfDigits() throws Exception {
        CreditCardDto obj = new CreditCardDto("alice", "1111 2222 333 4444 5555 6666", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberIsNotNumericOnly() throws Exception {
        CreditCardDto obj = new CreditCardDto("alice", "1111 -a*$ 333 4444", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    @Test
    public void testResponseIsUnprocessableEntityWhenCardNumberIsNotLunhSequence() throws Exception {
        CreditCardDto obj = new CreditCardDto("alice", "1111 2222 3333 4457", BigDecimal.valueOf(5000L));
        mockMvc.perform(post("/credit_cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonAsString(obj))
        ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", equalTo("Sequence is Not a Valid Card Number.")));
    }

    private String toJsonAsString(CreditCardDto dto) throws JsonProcessingException {
        return writer.writeValueAsString(dto);
    }

}