package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.MColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MColumnRepository extends JpaRepository<MColumn, Integer> {
    Collection<MColumn> getMColumnsByProjectIdOrderByDisplayOrder(Integer projectId);
}
