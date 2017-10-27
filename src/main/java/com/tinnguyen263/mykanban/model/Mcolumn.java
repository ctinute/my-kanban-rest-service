package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Mcolumn {
    private int id;
    private String name;
    private String description;
    private int displayOrder;
    private Integer cardLimit;

    private Project project;

    private Set<Card> cards;

    public Mcolumn() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "display_order", nullable = false)
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "card_limit", nullable = true)
    public Integer getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Integer cardLimit) {
        this.cardLimit = cardLimit;
    }

    @ManyToOne()
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @OneToMany(mappedBy = "mcolumn")
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mcolumn mcolumn = (Mcolumn) o;

        if (id != mcolumn.id) return false;
        if (displayOrder != mcolumn.displayOrder) return false;
        if (name != null ? !name.equals(mcolumn.name) : mcolumn.name != null) return false;
        if (description != null ? !description.equals(mcolumn.description) : mcolumn.description != null) return false;
        if (cardLimit != null ? !cardLimit.equals(mcolumn.cardLimit) : mcolumn.cardLimit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + displayOrder;
        result = 31 * result + (cardLimit != null ? cardLimit.hashCode() : 0);
        return result;
    }
}
