package com.tinnguyen263.mykanban.controller;

import java.util.List;

import com.tinnguyen263.mykanban.controller.ResponseObject.Error;
import com.tinnguyen263.mykanban.controller.ResponseObject.ResponseObject;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = {"application/json"})
	public ResponseEntity<List<User>> userDetails() {
		List<User> userDetails = userService.getAll();
		return new ResponseEntity<List<User>>(userDetails, HttpStatus.OK);
	}

    @RequestMapping(value = "/add/{username}/{email}/{password}/{name}", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseObject addser(@PathVariable String username,
                                 @PathVariable String email,
                                 @PathVariable String password,
                                 @PathVariable String name) {
        // check if user (username or email) is already existed
	    if (userService.getByKey(username) != null || userService.getByEmail(email) != null)
            return new ResponseObject(
                    false,
                    new Error("","User existed !!!",""),
                    null);
        else {
            User newUser = new User(username, email, password, name);
            // check if user is successfully saved to database
            if (userService.saveOrUpdate(newUser) != null)
                return new ResponseObject(
                        true,
                        new Error("","Success !!!",""),
                        null);
            else
                return new ResponseObject(
                    false,
                    new Error("","An error occurred !!!",""),
                    null);
        }
    }

}
