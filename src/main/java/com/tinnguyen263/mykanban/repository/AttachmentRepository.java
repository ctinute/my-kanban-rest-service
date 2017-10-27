package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Integer> {

}
