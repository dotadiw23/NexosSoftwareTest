package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.dto.TransactionDTO;
import com.credibanco.assessment.card.model.TransactionModel;
import com.credibanco.assessment.card.service.CardService;
import com.credibanco.assessment.card.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping()
    public TransactionDTO createTransaction(@RequestBody TransactionModel transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping
    public TransactionDTO cancelTransaction(@RequestBody TransactionModel transaction) {
        return transactionService.cancelTransaction(transaction);
    }

}
