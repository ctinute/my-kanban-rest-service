package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.TeamUserPK;

public interface TeamUserService extends AbstractEntityService<TeamUser, TeamUserPK> {
    boolean checkIfUserIsAdmin(Integer teamId, Integer userId);

    boolean checkIfUserIsMember(Integer teamId, Integer userId);
}
