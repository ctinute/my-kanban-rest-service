package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Checklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends CrudRepository<Checklist, Integer> {
}
