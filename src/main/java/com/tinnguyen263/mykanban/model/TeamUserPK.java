package com.tinnguyen263.mykanban.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TeamUserPK implements Serializable {
    private int teamId;
    private int userId;

    @Column(name = "team_id", nullable = false)
    @Id
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamUserPK that = (TeamUserPK) o;

        if (teamId != that.teamId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teamId;
        result = 31 * result + userId;
        return result;
    }
}
