package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Stage {
    private int id;
    private String name;
    private String description;
    private int displayOrder;
    private int cardLimit;

    private Ktable ktable;
    private Set<Card> cards;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "display_order")
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "card_limit")
    public int getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(int cardLimit) {
        this.cardLimit = cardLimit;
    }

    @ManyToOne()
    @JoinColumn(name = "table_id", nullable = false)
    public Ktable getKtable() {
        return ktable;
    }

    public void setKtable(Ktable ktable) {
        this.ktable = ktable;
    }

    @OneToMany(mappedBy = "stage")
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

        Stage stage = (Stage) o;

        if (id != stage.id) return false;
        if (displayOrder != stage.displayOrder) return false;
        if (cardLimit != stage.cardLimit) return false;
        if (name != null ? !name.equals(stage.name) : stage.name != null) return false;
        if (description != null ? !description.equals(stage.description) : stage.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + displayOrder;
        result = 31 * result + cardLimit;
        return result;
    }
}
