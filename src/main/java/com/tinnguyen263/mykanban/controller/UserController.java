package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
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
                         @RequestParam String password) {
        // checking if username already existed
        if (username != null && !username.isEmpty()) {
            // TODO: validate username
            if (userService.findByUsername(username) != null)
                return null; // TODO: throw UserNameExisted exception
        }
        // checking if email already existed
        if (email != null && !email.isEmpty()) {
            // TODO: validate email
            if (userService.findByEmail(email) != null)
                return null; // TODO: throw EmailExisted exception
        }

        // save new user
        return userService.saveOrUpdate(new User(username, email, name, password));
    }

    // update current user's info
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {"application/json"})
    public User updateMyInfo(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String name,
                             Principal principal) {
        String oldUsername = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(oldUsername);

        // checking if new username already existed
        if (username != null && !username.isEmpty()) {
            // TODO: validate username
            if (userService.findByUsername(username) != null)
                return null; // TODO: throw UserNameExisted exception
            else
                currentUser.setUsername(username);
        }
        // checking if email already existed
        if (email != null && !email.isEmpty()) {
            // TODO: validate email
            if (userService.findByEmail(email) != null)
                return null; // TODO: throw EmailExisted exception
            else
                currentUser.setEmail(email);
        }

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

    // update specific user ?
//    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = {"application/json"})
//    public User updateUser(@PathVariable Integer userId,
//                           @RequestParam(required = false) String newUsername,
//                           @RequestParam(required = false) String newEmail,
//                           @RequestParam(required = false) String newName) {
//        User user = userService.findByKey(userId);
//        // checking if new username already existed
//        if (newUsername != null && !newUsername.isEmpty())
//            if (userService.findByUsername(newUsername) == null)
//                user.setUsername(newUsername);
//            else
//                return null; // TODO: throw UserNameExisted exception
//
//        // checking if email already existed
//        if (newEmail != null && !newEmail.isEmpty())
//            if (userService.findByEmail(newEmail) == null)
//                user.setEmail(newEmail);
//            else
//                return null; // TODO: throw EmailExisted exception
//
//        if (newName != null && !newName.isEmpty())
//            user.setName(newName);
//
//        return userService.saveOrUpdate(user);
//    }

    // delete specific user ?
//    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE, produces = {"application/json"})
//    public void deleteUser(@PathVariable Integer userId) {
//        userService.deleteByKey(userId);
//    }

}
