package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.dto.CardDTO;
import com.credibanco.assessment.card.model.CardModel;
import com.credibanco.assessment.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping()
    public CardDTO createCard(@RequestBody CardModel card) {
        return this.cardService.createCard(card);
    }

    @PutMapping("/roll")
    public CardDTO rollCard(@RequestBody CardModel card) {
        return this.cardService.rollCard(card);
    }

    @GetMapping(path = "/{id}")
    public CardDTO getCard(@PathVariable("id") String id) {
        return this.cardService.getCard(id);
    }

    @DeleteMapping()
    public CardDTO deleteCard(@RequestBody CardModel card) {
        return this.cardService.deleteCard(card);
    }
}
