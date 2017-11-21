package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.Project;

public class ProjectMemberProjectWithTeamInfoDto extends ProjectMemberProjectDto {
    private TeamDto team;

    public ProjectMemberProjectWithTeamInfoDto() {
    }

    public ProjectMemberProjectWithTeamInfoDto(Project project, boolean isAdmin) {
        super(project, isAdmin);
        if (project.getTeam() != null)
            this.team = new TeamDto(project.getTeam());
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }
}
