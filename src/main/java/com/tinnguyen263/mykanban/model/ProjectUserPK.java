package com.tinnguyen263.mykanban.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ProjectUserPK implements Serializable {
    private int projectId;
    private int userId;

    @Column(name = "project_id", nullable = false)
    @Id
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

        ProjectUserPK that = (ProjectUserPK) o;

        if (projectId != that.projectId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + userId;
        return result;
    }
}
