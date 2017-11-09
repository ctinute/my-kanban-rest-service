package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.User;

public interface TeamService extends AbstractService<Team, Integer> {
    TeamUser addMember(Team team, User user, boolean isAdmin);

    boolean hasMember(Team team, User user);

    boolean hasAdmin(Team team, User user);
}
