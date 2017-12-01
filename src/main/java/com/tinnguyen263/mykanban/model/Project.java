package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @ManyToOne()
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @OneToMany(mappedBy = "project")
    private Collection<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "project")
    private Collection<Label> labels;

    @OneToMany(mappedBy = "project")
    private Collection<MColumn> mColumns;

    public Project() {
    }

    public Project(String name, String description, Boolean isPublic) {
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Collection<ProjectMember> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(Collection<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Collection<MColumn> getmColumns() {
        return mColumns;
    }

    public void setmColumns(Collection<MColumn> mColumns) {
        this.mColumns = mColumns;
    }
}
