package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.CardModel;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<CardModel, String> {

    public abstract CardModel findByNumber(String number);

    public abstract CardModel findBySystemId(String systemId);

    public abstract boolean existsByNumber(String number);

}
