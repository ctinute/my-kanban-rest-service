package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean queryDistinctFirstById(Integer projectId);
}
