package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "checklist_option", schema = "kanban", catalog = "")
public class ChecklistOption {
    private int id;
    private String name;
    private Boolean isCompleted;

    private Checklist checklist;

    public ChecklistOption() {
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
    @Column(name = "is_completed", nullable = false)
    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @ManyToOne()
    @JoinColumn(name = "checklist_id")
    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecklistOption that = (ChecklistOption) o;

        if (id != that.id) return false;
        if (isCompleted != that.isCompleted) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
