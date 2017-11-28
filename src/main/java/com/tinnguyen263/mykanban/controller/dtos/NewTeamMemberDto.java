package com.tinnguyen263.mykanban.controller.dtos;

public class NewTeamMemberDto {
    private int memberId;
    private boolean isAdmin;

    public NewTeamMemberDto(){}

    public NewTeamMemberDto(int memberId, boolean isAdmin) {
        this.memberId = memberId;
        this.isAdmin = isAdmin;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
