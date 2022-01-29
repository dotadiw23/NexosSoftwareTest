package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.TransactionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {

    public abstract TransactionModel findByReferenceNumber(String referenceNumber);
    public abstract boolean existsByReferenceNumber(String referenceNumber);

}
