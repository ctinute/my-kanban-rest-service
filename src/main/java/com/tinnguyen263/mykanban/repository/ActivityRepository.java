package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {

}
