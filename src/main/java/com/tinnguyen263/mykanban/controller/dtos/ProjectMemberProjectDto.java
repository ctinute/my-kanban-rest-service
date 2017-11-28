package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.Project;

public class ProjectMemberProjectDto extends ProjectDto {
    private boolean isAdmin;

    public ProjectMemberProjectDto() {
    }

    public ProjectMemberProjectDto(Project project, boolean isAdmin) {
        super(project);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
