package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json"})
    public User register(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String name,
                         @RequestParam String password) {
        // checking if username already existed
        if (userService.getByUsername(username) != null)
            return null; // ToDo: throw exception
        // checking if email already existed
        if (userService.getByEmail(email) != null)
            return null; // ToDo: throw exception

        // all good, save new user
        return userService.saveOrUpdate(new User(username, email, name, password));
    }
}
