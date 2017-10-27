package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {
    private int id;
    private String name;
    private String description;
    private byte isPublic;

    private Team team;

    private Set<Mcolumn> mcolumns;
    private Set<ProjectUser> projectUsers;

    public Project() {
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
    @Column(name = "is_public", nullable = false)
    public byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(byte isPublic) {
        this.isPublic = isPublic;
    }

    @ManyToOne()
    @JoinColumn(name = "team_id")
    public Team getTeam() {
        return team;
    }


    public void setTeam(Team team) {
        this.team = team;
    }

    @OneToMany(mappedBy = "project")
    public Set<Mcolumn> getMcolumns() {
        return mcolumns;
    }

    public void setMcolumns(Set<Mcolumn> mcolumns) {
        this.mcolumns = mcolumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (isPublic != project.isPublic) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (description != null ? !description.equals(project.description) : project.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) isPublic;
        return result;
    }
}
