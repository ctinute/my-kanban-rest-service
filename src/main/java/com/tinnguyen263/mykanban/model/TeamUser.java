package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "team_user", schema = "kanban")
public class TeamUser {

    @EmbeddedId
    private TeamUserPK teamUserPK;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @MapsId("teamUserPK.teamId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @MapsId("teamUserPK.userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public TeamUser() {
    }

    public TeamUser(Team team, User user, Boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.team = team;
        this.user = user;
        this.teamUserPK = new TeamUserPK(team.getId(), user.getId());
    }

    public TeamUserPK getTeamUserPK() {
        return teamUserPK;
    }

    public void setTeamUserPK(TeamUserPK teamUserPK) {
        this.teamUserPK = teamUserPK;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamUser teamUser = (TeamUser) o;

        return isAdmin != null ? isAdmin.equals(teamUser.isAdmin) : teamUser.isAdmin == null;
    }

    @Override
    public int hashCode() {
        return isAdmin != null ? isAdmin.hashCode() : 0;
    }

}
