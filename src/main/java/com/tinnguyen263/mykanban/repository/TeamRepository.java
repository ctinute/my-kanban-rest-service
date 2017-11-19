package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean queryDistinctFirstById(Integer teamId);
}
