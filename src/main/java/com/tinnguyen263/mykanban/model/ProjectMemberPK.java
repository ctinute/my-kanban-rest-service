package com.tinnguyen263.mykanban.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProjectMemberPK implements Serializable {

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "user_id")
    private Integer userId;

    public ProjectMemberPK() {
    }

    public ProjectMemberPK(Integer projectId, Integer userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectMemberPK that = (ProjectMemberPK) o;

        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
