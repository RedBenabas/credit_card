package com.publicis.creditcard.http.controller;

import com.publicis.creditcard.model.dto.CreditCardDto;
import com.publicis.creditcard.service.ICreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.publicis.creditcard.Utils.Constants.CREDIT_CARDS_ROUTE;

@RestController
@RequestMapping(value = CREDIT_CARDS_ROUTE)
public class CreditCardController {

    private ICreditCardService creditCardService;

    public CreditCardController(ICreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody @Valid CreditCardDto creditCardDto) {
        creditCardService.create(creditCardDto);
    }
}
