package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
}
