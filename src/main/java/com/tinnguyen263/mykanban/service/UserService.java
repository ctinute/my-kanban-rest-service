package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.User;

public interface UserService extends AbstractEntityService<User, Integer> {
    User findByEmail(String email);
    User findByUsername(String username);

    boolean checkEmailExisted(String email);

    boolean checkUsernameExisted(String username);
}
