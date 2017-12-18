package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Card {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String content;
    private Date dueTime;
    private int displayOrder;

    @ManyToOne
    @JoinColumn(name = "mcolumn_id", referencedColumnName = "id")
    private MColumn column;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "label_card",
            joinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id"))
    private Collection<Label> labels;

    public Card() {
    }

    public Card(String name, String content, Date dueTime, int displayOrder) {
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

    public MColumn getColumn() {
        return column;
    }

    public void setColumn(MColumn column) {
        this.column = column;
    }

    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }
}
