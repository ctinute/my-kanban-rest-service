/**
 *
 */
package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.repository.UserRepository;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getByKey(Integer k) {
        return null;
    }

    @Override
    public User saveOrUpdate(User o) {
        return null;
    }

    @Override
    public boolean deleteUser(Integer k) {
        return false;
    }
}
