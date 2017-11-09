package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.User;

public interface UserService extends AbstractService<User, Integer> {
    User findByEmail(String email);

    User findByUsername(String username);
}
