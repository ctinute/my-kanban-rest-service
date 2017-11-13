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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByKey(Integer k) {
        return userRepository.findOne(k);
    }

    @Override
    public User saveOrUpdate(User o) {
        o.setPassword(passwordEncoder.encode(o.getPassword()));
        return userRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        userRepository.delete(k);
    }


    /*
    * OTHER METHODS
    * */

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
