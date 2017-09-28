package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Card {
    private int id;
    private String title;
    private String content;
    private Date dueDate;
    private int displayOrder;

    private Ktable ktable;
    private Stage stage;
    private Set<Tag> tags;

    public Card(){}

    public Card(String title, String content, Date dueDate, int displayOrder) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.displayOrder = displayOrder;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "due_date")
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "display_order")
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @ManyToOne()
    @JoinColumn(name = "table_id", nullable = false)
    public Ktable getKtable() {
        return ktable;
    }

    public void setKtable(Ktable ktable) {
        this.ktable = ktable;
    }

    @ManyToOne()
    @JoinColumn(name = "stage_id", nullable = false)
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "card_tag",
            joinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (displayOrder != card.displayOrder) return false;
        if (title != null ? !title.equals(card.title) : card.title != null) return false;
        if (content != null ? !content.equals(card.content) : card.content != null) return false;
        if (dueDate != null ? !dueDate.equals(card.dueDate) : card.dueDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + displayOrder;
        return result;
    }
}
