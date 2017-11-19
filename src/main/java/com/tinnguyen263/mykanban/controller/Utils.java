package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;

class Utils {

    static String getUsernameFromPrincipal(Principal principal) {
        return ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
    }

    static User getCurrentUserFromPrincipal(Principal principal, UserService userService) {
        return userService.findByUsername(getUsernameFromPrincipal(principal));
    }
}
