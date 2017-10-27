package com.tinnguyen263.mykanban.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class Activity {
    private int id;
    private String operation;
    private String objType;
    private int objId;
    private Time time;

    private Project project;
    private User user;

    public Activity() {
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
    @Column(name = "operation", nullable = false, length = 64)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "obj_type", nullable = false, length = 64)
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Basic
    @Column(name = "obj_id", nullable = false)
    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    @ManyToOne()
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (id != activity.id) return false;
        if (objId != activity.objId) return false;
        if (operation != null ? !operation.equals(activity.operation) : activity.operation != null) return false;
        if (objType != null ? !objType.equals(activity.objType) : activity.objType != null) return false;
        if (time != null ? !time.equals(activity.time) : activity.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (objType != null ? objType.hashCode() : 0);
        result = 31 * result + objId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
