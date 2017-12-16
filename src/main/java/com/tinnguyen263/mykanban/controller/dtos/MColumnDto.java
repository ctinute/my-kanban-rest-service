package com.tinnguyen263.mykanban.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinnguyen263.mykanban.model.MColumn;

public class MColumnDto {

    private Integer id;
    private String name;
    private String description;
    private int displayOrder;
    private int cardLimit;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer projectId;

    public MColumnDto() {
    }

    public MColumnDto(MColumn mColumn) {
        this.id = mColumn.getId();
        this.name = mColumn.getName();
        this.description = mColumn.getDescription();
        this.displayOrder = mColumn.getDisplayOrder();
        this.cardLimit = mColumn.getCardLimit();
    }

    public MColumnDto(String name, String description, int displayOrder, int cardLimit) {
        this.name = name;
        this.description = description;
        this.displayOrder = displayOrder;
        this.cardLimit = cardLimit;
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

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(int cardLimit) {
        this.cardLimit = cardLimit;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
