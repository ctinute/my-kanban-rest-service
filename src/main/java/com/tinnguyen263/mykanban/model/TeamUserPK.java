package com.tinnguyen263.mykanban.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TeamUserPK implements Serializable {

    private Integer teamId;
    private Integer userId;

    public TeamUserPK() {
    }

    public TeamUserPK(Integer teamId, Integer userId) {
        this.teamId = teamId;
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamUserPK that = (TeamUserPK) o;

        return (teamId != null ? teamId.equals(that.teamId) : that.teamId == null) && (userId != null ? userId.equals(that.userId) : that.userId == null);
    }

    @Override
    public int hashCode() {
        int result = teamId != null ? teamId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
