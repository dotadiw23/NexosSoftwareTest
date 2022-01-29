package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.CardModel;
import com.credibanco.assessment.card.model.TransactionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {

    public abstract TransactionModel findByReferenceNumberAndCardId(String referenceNumber, CardModel card);
    public abstract boolean existsByReferenceNumber(String referenceNumber);
    public abstract List<TransactionModel> findAllByCardId(CardModel cardId);

}
