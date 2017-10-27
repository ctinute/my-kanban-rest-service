package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "project_user", schema = "kanban", catalog = "")
@IdClass(ProjectUserPK.class)
public class ProjectUser {
    private int projectId;
    private int userId;
    private byte isAdmin;

    public ProjectUser() {
    }

    @Id
    @Column(name = "project_id", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

        ProjectUser that = (ProjectUser) o;

        if (projectId != that.projectId) return false;
        if (userId != that.userId) return false;
        if (isAdmin != that.isAdmin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + userId;
        result = 31 * result + (int) isAdmin;
        return result;
    }
}
