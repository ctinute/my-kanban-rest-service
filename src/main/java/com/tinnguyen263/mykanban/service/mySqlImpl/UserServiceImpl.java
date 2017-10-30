/**
 *
 */
package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.repository.UserRepository;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByKey(Integer k) {
        return userRepository.findOne(k);
    }

    @Override
    public User saveOrUpdate(User o) {
        o.setPassword(passwordEncoder.encode(o.getPassword()));
        System.out.print("password hash: " + o.getPassword());
        return userRepository.save(o);
    }

    @Override
    public void deleteUser(Integer k) {
        userRepository.delete(k);
    }
}
