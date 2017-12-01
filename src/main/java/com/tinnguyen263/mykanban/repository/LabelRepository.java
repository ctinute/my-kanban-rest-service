package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
}
