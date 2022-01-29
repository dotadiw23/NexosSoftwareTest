package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.CardDTO;
import com.credibanco.assessment.card.dto.TransactionDTO;
import com.credibanco.assessment.card.model.CardModel;
import com.credibanco.assessment.card.model.TransactionModel;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.card.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CardRepository cardRepository;

    public TransactionDTO createTransaction(TransactionModel transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        try {
            if (transaction.getCardId() == null || transaction.getReferenceNumber().isBlank() || transaction.getTotal() == null
                    || transaction.getAddress().isBlank()) {
                incompleteFormError(transactionDTO);
                transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
                return transactionDTO;
            }
        }catch (Exception e) {
            e.printStackTrace();
            incompleteFormError(transactionDTO);
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            return transactionDTO;
        }

        if (transaction.getTotal() <= 0) {
            transactionDTO.setResponseCode("04");
            transactionDTO.setMessage("Invalid value!");
            return transactionDTO;
        }

        CardModel dbCard = cardRepository.findBySystemId(transaction.getCardId().getSystemId());

        if (dbCard == null) {
            transactionDTO.setResponseCode("01");
            transactionDTO.setMessage("The card does not exists");
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            return transactionDTO;
        } else if (!dbCard.getStatus().equals(CardDTO.TYPE_ROLlED)) {
            transactionDTO.setResponseCode("02");
            transactionDTO.setMessage("The card isn't 'rolled' yet");
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            return transactionDTO;
        }

        if (!transactionRepository.existsByReferenceNumber(transaction.getReferenceNumber())) {
            transaction.setTransactionDate(new Date());
            transaction.setLastUpdate(new Date());
            transaction.setStatus(TransactionDTO.STATUS_APPROVED);
            transactionRepository.save(transaction);
            transactionDTO.setResponseCode("00");
            transactionDTO.setMessage("Successful purchase!");
            transactionDTO.setReferenceNumber(transaction.getReferenceNumber());
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_APPROVED);
        }else {
            transactionDTO.setResponseCode("04");
            transactionDTO.setMessage("The transaction is already created");
            transactionDTO.setReferenceNumber(transaction.getReferenceNumber());
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
        }
        return transactionDTO;
    }

    public TransactionDTO cancelTransaction(TransactionModel transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        if (transaction.getCardId() == null || transaction.getReferenceNumber() == null
                || transaction.getTotal() == null) {
            incompleteFormError(transactionDTO);
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            return  transactionDTO;
        }

        CardModel dbCard = cardRepository.findBySystemId(transaction.getCardId().getSystemId());

        if (dbCard == null) {
            transactionDTO.setResponseCode("01");
            transactionDTO.setMessage("The card does not exists");
            transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            return transactionDTO;
        } else {
            TransactionModel dbTransaction = transactionRepository.findByReferenceNumberAndCardId(transaction.getReferenceNumber(), dbCard);
            if (dbTransaction == null) {
                transactionDTO.setResponseCode("01");
                transactionDTO.setMessage("Reference number not found");
                transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
                return transactionDTO;
            }

            if (dbTransaction.getTotal().compareTo(transaction.getTotal()) != 0) {
                transactionDTO.setResponseCode("02");
                transactionDTO.setMessage("The transaction value doesn't match!");
                transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
                return transactionDTO;
            }

            if (!dbCard.getStatus().equals(CardDTO.TYPE_ROLlED)) {
                transactionDTO.setResponseCode("02");
                transactionDTO.setMessage("The card isn't 'rolled' yet");
                transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
                return transactionDTO;
            }

            long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(
                    new Date().getTime() - dbTransaction.getTransactionDate().getTime());
            if (minutesDiff < 5) {
                if (!dbTransaction.getStatus().equals(TransactionDTO.STATUS_CANCELLED)) {
                    dbTransaction.setLastUpdate(new Date());
                    dbTransaction.setStatus(TransactionDTO.STATUS_CANCELLED);
                    transactionRepository.save(dbTransaction);
                }
                transactionDTO.setResponseCode("00");
                transactionDTO.setMessage("Transaction has been cancelled successfully!");
                transactionDTO.setReferenceNumber(dbTransaction.getReferenceNumber());
            } else {
                transactionDTO.setResponseCode("02");
                transactionDTO.setMessage("The transaction cannot be cancelled");
                transactionDTO.setTransactionStatus(TransactionDTO.STATUS_REJECTED);
            }
            return transactionDTO;
        }
    }

    public List<TransactionDTO> getTransactions(String cardId) {
        List<TransactionDTO> transactionsList = new ArrayList<>();
        CardModel dbCard = new CardModel();
        dbCard.setSystemId(cardId);
        List<TransactionModel> transactions = (List<TransactionModel>) transactionRepository.findAllByCardId(dbCard);
        for (TransactionModel transaction:
             transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(transaction.getTransactionDate(),
                    transaction.getReferenceNumber(), transaction.getStatus(), transaction.getTotal());
            transactionsList.add(transactionDTO);
        }
        return transactionsList;
    }

    private void incompleteFormError(TransactionDTO transactionDTO) {
        transactionDTO.setResponseCode("04");
        transactionDTO.setMessage("Data missing! Please complete the form");
    }
}
