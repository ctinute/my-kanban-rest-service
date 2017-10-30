package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {"application/json"})
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> getUser(
            @PathVariable String uid,
            @RequestBody User user
    ) {
        return null;
    }


    @RequestMapping(value = "/update/{uid}", method = RequestMethod.DELETE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> updateUser(
            @PathVariable String uid,
            @RequestBody User user
    ) {
        return null;
    }


    @RequestMapping(value = "/update/{uid}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(
            @PathVariable String uid
    ) {
        return null;
    }

}
