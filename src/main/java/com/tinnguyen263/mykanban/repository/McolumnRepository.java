package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Mcolumn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface McolumnRepository extends CrudRepository<Mcolumn, Integer> {
}
