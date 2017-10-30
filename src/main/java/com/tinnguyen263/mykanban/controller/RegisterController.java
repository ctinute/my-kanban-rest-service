package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password
    ) {
        if (userService.getByUsername(username) != null)
            return new ResponseEntity<>("Username existed", HttpStatus.CONFLICT);
        if (userService.getByEmail(email) != null)
            return new ResponseEntity<>("Email existed", HttpStatus.CONFLICT);
        User newUser = userService.saveOrUpdate(new User(username, email, name, password));
        if (newUser != null)
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        return new ResponseEntity<Object>("Somethig happenned", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
