package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.ProjectMember;

public class ProjectMemberDto {
    private Integer memberId;
    private Boolean isAdmin;

    public ProjectMemberDto() {
    }

    public ProjectMemberDto(Integer memberId, Boolean isAdmin) {
        this.memberId = memberId;
        this.isAdmin = isAdmin;
    }

    public ProjectMemberDto(ProjectMember projectMember) {
        memberId = projectMember.getProjectMemberPK().getUserId();
        isAdmin = projectMember.getAdmin();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
