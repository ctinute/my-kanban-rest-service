/**
 * 
 */
package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.User;

public interface UserService extends AbstractService<User, String> {
    public User getByEmail(String email);
}
