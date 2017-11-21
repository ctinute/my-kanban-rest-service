package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.Team;

public class TeamUserTeamDto extends TeamDto {
    private boolean isAdmin;

    public TeamUserTeamDto() {
    }

    public TeamUserTeamDto(Team team, boolean isAdmin) {
        super(team);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
