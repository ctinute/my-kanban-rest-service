package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "project_member", schema = "kanban", catalog = "")
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberPK projectMemberPK;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @MapsId("projectMemberPK.projectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @MapsId("projectMemberPK.userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ProjectMember() {
    }

    public ProjectMember(Project project, User user, Boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.project = project;
        this.user = user;
        this.projectMemberPK = new ProjectMemberPK(project.getId(), user.getId());
    }

    public ProjectMemberPK getProjectMemberPK() {
        return projectMemberPK;
    }

    public void setProjectMemberPK(ProjectMemberPK projectMemberPK) {
        this.projectMemberPK = projectMemberPK;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

        ProjectMember that = (ProjectMember) o;

        if (isAdmin != null ? !isAdmin.equals(that.isAdmin) : that.isAdmin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isAdmin != null ? isAdmin.hashCode() : 0;
    }
}
