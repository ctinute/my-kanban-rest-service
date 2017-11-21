package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.ProjectMember;
import com.tinnguyen263.mykanban.model.ProjectMemberPK;

public interface ProjectMemberService extends AbstractEntityService<ProjectMember, ProjectMemberPK> {
    boolean checkIfUserIsAdmin(Integer projectId, Integer userId);

    boolean checkIfUserIsMember(Integer projectId, Integer userId);
}
