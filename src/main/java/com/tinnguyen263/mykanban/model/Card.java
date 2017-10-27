package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

@Entity
public class Card {

    private int id;
    private String name;
    private String content;
    private Time dueTime;

    private Mcolumn mcolumn;
    private Set<Label> labels;
    private Set<User> subscribedUser;
    private Set<User> assignedUser;
    private Set<Attachment> attachments;
    private Set<Comment> comments;
    private Set<Checklist> checklists;

    public Card() {
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
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "due_time", nullable = true)
    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }


    @ManyToOne()
    @JoinColumn(name = "mcolumn_id")
    public Mcolumn getMcolumn() {
        return mcolumn;
    }

    public void setMcolumn(Mcolumn mcolumn) {
        this.mcolumn = mcolumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        if (content != null ? !content.equals(card.content) : card.content != null) return false;
        if (dueTime != null ? !dueTime.equals(card.dueTime) : card.dueTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dueTime != null ? dueTime.hashCode() : 0);
        return result;
    }
}
