package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
import com.tinnguyen263.mykanban.exceptions.EmailExistedException;
import com.tinnguyen263.mykanban.exceptions.UsernameExistedException;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    * mapping '/api/users"
    * */

    // get current user's info
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public User getMyInfo(Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        return userService.findByUsername(username);
    }

    // register a new user
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json"})
    public User register(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String name,
                         @RequestParam String password) throws EmailExistedException, UsernameExistedException {
        // checking if new username already existed
        if (username != null && !username.isEmpty())
            if (userService.findByUsername(username) != null)
                throw new UsernameExistedException();

        // checking if email already existed
        if (email != null && !email.isEmpty())
            if (userService.findByEmail(email) != null)
                throw new EmailExistedException();

        // save new user
        return userService.saveOrUpdate(new User(username, email, name, password));
    }

    // update current user's info
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {"application/json"})
    public User updateMyInfo(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String name,
                             Principal principal) throws EmailExistedException, UsernameExistedException {
        String oldUsername = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(oldUsername);

        // checking if new username already existed
        if (username != null && !username.isEmpty())
            if (userService.findByUsername(username) != null)
                throw new UsernameExistedException();
            else
                currentUser.setUsername(username);

        // checking if email already existed
        if (email != null && !email.isEmpty())
            if (userService.findByEmail(email) != null)
                throw new EmailExistedException();
            else
                currentUser.setEmail(email);

        if (name != null && !name.isEmpty())
            currentUser.setName(name);

        return userService.saveOrUpdate(currentUser);
    }



    /*
    * mapping '/api/users/{userId}"
    * */

    // get specific user
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public User getUser(@PathVariable Integer userId) {
        return userService.findByKey(userId);
    }

    // delete specific user ?
    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE, produces = {"application/json"})
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteByKey(userId);
    }

}
