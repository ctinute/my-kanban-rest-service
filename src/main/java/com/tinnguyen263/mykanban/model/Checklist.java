package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Checklist {
    private int id;
    private String name;

    private Card card;
    private Set<ChecklistOption> options;

    public Checklist() {
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

    @ManyToOne()
    @JoinColumn(name = "card_id")
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @OneToMany(mappedBy = "checklist")
    public Set<ChecklistOption> getOptions() {
        return options;
    }

    public void setOptions(Set<ChecklistOption> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checklist checklist = (Checklist) o;

        if (id != checklist.id) return false;
        if (name != null ? !name.equals(checklist.name) : checklist.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
