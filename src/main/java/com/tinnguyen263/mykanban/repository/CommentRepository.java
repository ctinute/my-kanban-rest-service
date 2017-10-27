package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
