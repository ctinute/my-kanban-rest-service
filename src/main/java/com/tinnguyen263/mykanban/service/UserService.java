package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.User;

public interface UserService extends AbstractService<User, Integer> {
    public User getByEmail(String email);
    public User getByUsername(String username);
}
