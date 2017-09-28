package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    private int id;
    private String name;

    private Set<Action> actions;
    private Set<User> users;

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, Set<Action> actions) {
        this.name = name;
        this.actions = actions;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_action",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "action_id", referencedColumnName = "id")
    )
    public Set<Action> getActions() {
        return actions;
    }

    public void setBooks(Set<Action> books) {
        this.actions = actions;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_action",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "action_id", referencedColumnName = "id")
    )
    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @ManyToMany(mappedBy = "roles")
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

        Role role = (Role) o;

        if (id != role.id) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
