package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
