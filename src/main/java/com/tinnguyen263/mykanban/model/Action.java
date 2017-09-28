package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Action {
    private int id;
    private String name;
    private String action;
    private ActionObjectType objectType;
    private int objectId;

    private Set<Role> roles;
    private Set<User> users;

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
    @Column(name = "action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ActionObjectType getObjectType() {
        return objectType;
    }


    @Basic
    @Column(name = "object_type")
    @Enumerated(EnumType.STRING)
    public void setObjectType(ActionObjectType objectType) {
        this.objectType = objectType;
    }

    @Basic
    @Column(name = "object_id")
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @ManyToMany(mappedBy = "actions")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "actions")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action1 = (Action) o;

        if (id != action1.id) return false;
        if (objectId != action1.objectId) return false;
        if (name != null ? !name.equals(action1.name) : action1.name != null) return false;
        if (action != null ? !action.equals(action1.action) : action1.action != null) return false;
        if (objectType != null ? !objectType.equals(action1.objectType) : action1.objectType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (objectType != null ? objectType.hashCode() : 0);
        result = 31 * result + objectId;
        return result;
    }
}
