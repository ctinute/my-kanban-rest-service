package com.tinnguyen263.mykanban.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinnguyen263.mykanban.model.Project;

public class ProjectDto {

    private Integer id;
    private String name;
    private String description;
    private Boolean isPublic;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer teamId;

//        @JsonIgnore
//        private Collection<MemberDto> members;

    public ProjectDto() {
    }

    public ProjectDto(Integer id, String name, String description, Boolean isPublic, Integer teamId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.teamId = teamId;
    }

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.isPublic = project.getIsPublic();
        this.teamId = project.getTeam() != null ? project.getTeam().getId() : null;
//            members = new ArrayList<>();
//            for (ProjectMember pm : project.getProjectMembers())
//                members.add(new MemberDto(pm));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}