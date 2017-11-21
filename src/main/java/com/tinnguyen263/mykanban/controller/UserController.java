package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.TeamUserTeamDto;
import com.tinnguyen263.mykanban.controller.dtos.UserDto;
import com.tinnguyen263.mykanban.exceptions.EmailExistedException;
import com.tinnguyen263.mykanban.exceptions.UsernameExistedException;
import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.TeamService;
import com.tinnguyen263.mykanban.service.TeamUserService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TeamUserService teamUserService;
    private final TeamService teamService;

    @Autowired
    public UserController(UserService userService, TeamUserService teamUserService, TeamService teamService) {
        this.userService = userService;
        this.teamUserService = teamUserService;
        this.teamService = teamService;
    }

    /*
    * mapping '/api/users"
    * */

    // get current user's info
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public UserDto getUser(Principal principal) {
        return new UserDto(userService.findByUsername(Utils.getUsernameFromPrincipal(principal)));
    }

    // register a new user
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json"})
    public UserDto register(@RequestBody UserDto user) throws EmailExistedException, UsernameExistedException {
        // checking if new username already existed
        if (user.getUsername() != null && !user.getUsername().isEmpty())
            if (userService.findByUsername(user.getUsername()) != null)
                throw new UsernameExistedException();

        // checking if email already existed
        if (user.getEmail() != null && !user.getEmail().isEmpty())
            if (userService.findByEmail(user.getEmail()) != null)
                throw new EmailExistedException();

        // save new user
        return new UserDto(userService.saveOrUpdate(toEntity(user)));
    }

    // update current user's info
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {"application/json"})
    public UserDto updateUser(@RequestBody UserDto user,
                              Principal principal) throws UsernameExistedException, EmailExistedException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        // checking if new username already existed
        if (user.getUsername() != null && !user.getUsername().isEmpty() && !user.getUsername().equals(currentUser.getUsername()))
            if (userService.findByUsername(user.getUsername()) != null)
                throw new UsernameExistedException();
            else
                currentUser.setUsername(user.getUsername());

        // checking if email already existed
        if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().equals(currentUser.getEmail()))
            if (userService.findByEmail(user.getEmail()) != null)
                throw new EmailExistedException();
            else
                currentUser.setEmail(user.getEmail());

        if (user.getName() != null && !user.getName().isEmpty())
            currentUser.setName(user.getName());

        return new UserDto(userService.saveOrUpdate(currentUser));
    }

    // delete current user
//    @RequestMapping(value = "", method = RequestMethod.DELETE)
//    public void deleteUser(Principal principal) {
//        userService.deleteByKey(Utils.getCurrentUserFromPrincipal(principal, userService).getId());
//    }



    /*
    * mapping '/api/users/{userId}"
    * */

    // get specific user
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public UserDto getSpecificUser(@PathVariable Integer userId) {
        return new UserDto(userService.findByKey(userId));
    }

    /*
    * mapping /api/users/{userId}/teams
    * */
    @RequestMapping(value = "/{userId}/teams", method = RequestMethod.GET)
    public Collection<TeamUserTeamDto> getTeamsOfSpecificUser(@PathVariable Integer userId,
                                                              Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        User user = userService.findByKey(userId);
        Collection<TeamUser> teamUserList = user.getTeamUsers();
        Collection<TeamUser> accessibleTeamUserList = new ArrayList<>();
        for (TeamUser t : teamUserList) {
            Team team = teamService.findByKey(t.getTeamUserPK().getTeamId());
            if (team.getPublic() || teamUserService.checkIfUserIsMember(team.getId(), currentUser.getId())) {
                accessibleTeamUserList.add(t);
            }
        }
        Collection<TeamUserTeamDto> teamUserTeamDtos = new ArrayList<>();
        for (TeamUser tu : accessibleTeamUserList)
            teamUserTeamDtos.add(new TeamUserTeamDto(tu.getTeam(), tu.getAdmin()));
        return teamUserTeamDtos;
    }


    private User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }

}
