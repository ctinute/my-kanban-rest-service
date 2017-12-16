package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "mcolumn")
public class MColumn {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private int displayOrder;
    private int cardLimit;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "column")
    private Collection<Card> cards;

    public MColumn() {
    }

    public MColumn(String name, String description, int displayOrder, int cardLimit) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Collection<Card> getCards() {
        return cards;
    }

    public void setCards(Collection<Card> cards) {
        this.cards = cards;
    }
}
