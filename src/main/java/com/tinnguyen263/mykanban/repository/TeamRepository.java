package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
}
