package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.ProjectMember;
import com.tinnguyen263.mykanban.model.ProjectMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberPK
        > {
}
