package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;

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

    // this controller return all users
    // TODO: delete on release
    @PermitAll
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public List<User> getAllUsers() {
        return userService.getAll();
    }


    // this controller is used for update current user's info
    // for registering, check out RegisterController.java with "/register" mapping
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json"})
    public User updateMyInfo(@RequestParam(required = false) String newUsername,
                             @RequestParam(required = false) String newEmail,
                             @RequestParam(required = false) String newName,
                             Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);

        // checking if new username already existed
        if (newUsername != null && !newUsername.isEmpty())
            if (userService.getByUsername(newUsername) == null)
                currentUser.setUsername(newUsername);
            else
                return null; // TODO: throw UserNameExisted exception

        // checking if email already existed
        if (newEmail != null && !newEmail.isEmpty())
            if (userService.getByEmail(newEmail) == null)
                currentUser.setEmail(newEmail);
            else
                return null; // TODO: throw EmailExisted exception

        if (newName != null && !newName.isEmpty())
            currentUser.setName(newName);

        return userService.saveOrUpdate(currentUser);
    }

    /*
    * mapping '/api/users/me"
    * */
    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = {"application/json"})
    public User getMyInfo(Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        return userService.getByUsername(username);
    }


    /*
    * mapping '/api/users/{userId}"
    * */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public User getUser(@PathVariable Integer userId) {
        return userService.getByKey(userId);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST, produces = {"application/json"})
    public User updateUser(@PathVariable Integer userId,
                           @RequestParam(required = false) String newUsername,
                           @RequestParam(required = false) String newEmail,
                           @RequestParam(required = false) String newName) {
        User user = userService.getByKey(userId);
        // checking if new username already existed
        if (newUsername != null && !newUsername.isEmpty())
            if (userService.getByUsername(newUsername) == null)
                user.setUsername(newUsername);
            else
                return null; // TODO: throw UserNameExisted exception

        // checking if email already existed
        if (newEmail != null && !newEmail.isEmpty())
            if (userService.getByEmail(newEmail) == null)
                user.setEmail(newEmail);
            else
                return null; // TODO: throw EmailExisted exception

        if (newName != null && !newName.isEmpty())
            user.setName(newName);

        return userService.saveOrUpdate(user);
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE, produces = {"application/json"})
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }

}
