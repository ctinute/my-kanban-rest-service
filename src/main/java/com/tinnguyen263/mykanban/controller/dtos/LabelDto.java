package com.tinnguyen263.mykanban.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinnguyen263.mykanban.model.Label;

public class LabelDto {

    private Integer id;
    private String name;
    private String color;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer projectId;

    public LabelDto() {
    }

    public LabelDto(Integer id, String name, String color, Integer projectId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.projectId = projectId;
    }

    public LabelDto(Label label) {
        this.id = label.getId();
        this.name = label.getName();
        this.color = label.getColor();
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
