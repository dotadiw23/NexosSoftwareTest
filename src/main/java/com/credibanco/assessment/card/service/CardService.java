package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.CardDTO;
import com.credibanco.assessment.card.model.CardModel;
import com.credibanco.assessment.card.repository.CardRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public CardDTO createCard(CardModel card) {

        CardDTO cardDTO = new CardDTO();

        if (card.getNumber() == null || card.getOwner() == null || card.getDocumentId() == null
                || card.getType() == null || card.getPhone() == null) {
            incompleteFormError(cardDTO);
            return cardDTO;
        }
        if (!card.getType().equals(CardDTO.TYPE_CREDIT) && !card.getType().equals(CardDTO.TYPE_DEBIT)) {
            cardDTO.setResponseCode("01");
            cardDTO.setMessage("Invalid card type!");
            return cardDTO;
        }

        // If the request data is correct, save the card
        card.setSecurityCode(Integer.toString((int) Math.floor(Math.random() * 101)));
        card.setNumber(card.getNumber().trim());
        card.setStatus(CardDTO.TYPE_CREATED);
        card.setSystemId(DigestUtils.sha256Hex(card.getNumber()));
        cardRepository.save(card);

        // Response
        cardDTO.setResponseCode("00");
        cardDTO.setMessage("Card created successfully!");
        cardDTO.setValidationCode(card.getSecurityCode());
        cardDTO.setCardNumber(hideCardNumbers(card.getNumber()));
        cardDTO.setSystemId(card.getSystemId());
        return cardDTO;
    }

    private String hideCardNumbers(String number) {
        return number.substring(0, 4) + "****" + number.substring(number.length() - 4, number.length());
    }

    public CardDTO rollCard(CardModel card) {
        CardDTO cardDTO = new CardDTO();

        if (card.getNumber() == null | card.getSecurityCode() == null) {
            incompleteFormError(cardDTO);
            return cardDTO;
        }

        CardModel dbCard = cardRepository.findByNumber(card.getNumber());
        if (dbCard == null) {
            cardDTO.setResponseCode("01");
            cardDTO.setMessage("Card number does not exists");
            return cardDTO;
        }else if (!dbCard.getSecurityCode().equals(card.getSecurityCode())) {
            cardDTO.setResponseCode("02");
            cardDTO.setMessage("The card security code does not match!");
            return cardDTO;
        }else {
            dbCard.setStatus(CardDTO.TYPE_ROLlED);
            cardRepository.save(dbCard);
            cardDTO.setResponseCode("00");
            cardDTO.setMessage("The card has been 'rolled' successfully!");
            cardDTO.setCardNumber(hideCardNumbers(dbCard.getNumber()));
            return cardDTO;
        }

    }

    public CardDTO getCard(String id) {
        CardDTO cardDTO = new CardDTO();
        CardModel card = cardRepository.findBySystemId(id);

        if (card != null) {
            cardDTO.setCardNumber(hideCardNumbers(card.getNumber()));
            cardDTO.setOwner(card.getOwner());
            cardDTO.setDocumentId(card.getDocumentId());
            cardDTO.setStatus(card.getStatus());
            return cardDTO;
        }else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Card not found!"
            );
        }
    }

    public CardDTO deleteCard(CardModel card) {
        CardDTO cardDTO = new CardDTO();
        if (card.getSystemId() == null | card.getSecurityCode() == null || card.getNumber() == null) {
            incompleteFormError(cardDTO);
            return cardDTO;
        }

        CardModel dbCard = cardRepository.findBySystemId(card.getSystemId());
        if (dbCard != null) {
            if (dbCard.getNumber().trim().equals(card.getNumber().trim())
                    && dbCard.getSecurityCode().equals(card.getSecurityCode())) {
                cardRepository.delete(dbCard);
                cardDTO.setResponseCode("00");
                cardDTO.setMessage("The card has been deleted successfully!");
            }else {
                cardDTO.setResponseCode("01");
                cardDTO.setMessage("The card cannot be deleted");
            }
        }else {
            cardDTO.setResponseCode("01");
            cardDTO.setMessage("The card cannot be deleted");
        }

        return cardDTO;
    }

    private void incompleteFormError(CardDTO cardDTO) {
        cardDTO.setResponseCode("01");
        cardDTO.setMessage("Data missing! Please complete the form");
    }

}
