package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.User;

public class TeamUserUserDto extends UserDto {
    private boolean isAdmin;

    public TeamUserUserDto() {
    }

    public TeamUserUserDto(User user, boolean isAdmin) {
        super(user);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
