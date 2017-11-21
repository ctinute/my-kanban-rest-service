package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.Team;

public class TeamDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean isPublic;

    public TeamDto() {
    }

    public TeamDto(Integer id, String name, String description, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.isPublic = team.getPublic();
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
}
