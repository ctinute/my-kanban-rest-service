package com.tinnguyen263.mykanban.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinnguyen263.mykanban.model.Card;

import java.util.Date;

public class CardDto {
    private Integer id;
    private String name;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dueTime;

    private int displayOrder;

    private Integer columnId;

    public CardDto() {
    }

    public CardDto(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        this.content = card.getContent();
        this.dueTime = card.getDueTime();
        this.displayOrder = card.getDisplayOrder();
        this.columnId = card.getColumn().getId();
    }

    public CardDto(String name, String content, Date dueTime, int displayOrder) {
        this.name = name;
        this.content = content;
        this.dueTime = dueTime;
        this.displayOrder = displayOrder;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
}
