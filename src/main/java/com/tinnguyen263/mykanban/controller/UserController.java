package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PermitAll
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

    @PermitAll
    @RequestMapping(value = "/registration/{username}/{password}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> regUser(@PathVariable String username, @PathVariable String password) {
        User newUser = new User(username, "some mail", password, "some name");
        // check if user is successfully saved to database
        if (userService.saveOrUpdate(newUser) != null)
            return new ResponseEntity<String>("User has been successfully added", new HttpHeaders(), HttpStatus.OK);
        else
            return new ResponseEntity<String>("Error occurred when adding user", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);


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
