package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "team_user", schema = "kanban", catalog = "")
@IdClass(TeamUserPK.class)
public class TeamUser {
    private int teamId;
    private int userId;
    private byte isAdmin;

    public TeamUser() {
    }

    @Id
    @Column(name = "team_id", nullable = false)
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamUser teamUser = (TeamUser) o;

        if (teamId != teamUser.teamId) return false;
        if (userId != teamUser.userId) return false;
        if (isAdmin != teamUser.isAdmin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teamId;
        result = 31 * result + userId;
        result = 31 * result + (int) isAdmin;
        return result;
    }
}
