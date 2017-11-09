package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.TeamService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    private final UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }


    /*
    * mapping "/api/teams"
    * */

    // get current user's teams
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public List<TeamUser> getMyTeams(Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        return (List<TeamUser>) user.getTeamUsers();
    }

    // create new team
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json"})
    public Team addTeam(@RequestParam String name,
                        @RequestParam(required = false, defaultValue = "") String description,
                        @RequestParam(required = false, defaultValue = "false") boolean isPublic,
                        Principal principal) {
        // save team and get id back
        Team newTeam = teamService.saveOrUpdate(new Team(name, description, isPublic));

        // user who create team will be default team admin
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username);
        teamService.addMember(newTeam, currentUser, true);

        return newTeam;
    }



    /*
    * mapping "/api/teams/(teamId)
    * */

    // get specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET, produces = {"application/json"})
    public Team getTeam(@PathVariable Integer teamId,
                        Principal principal) {
        Team team = teamService.findByKey(teamId);

        // checking if team is public for anyone to view
        if (team.getPublic())
            return team;

        // checking if current user is member of team (in case team is not public, only member can access team info)
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username);
        if (teamService.hasMember(team, currentUser))
            return team;
        else
            return null; // TODO: throw exception
    }

    // update specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.PUT, produces = {"application/json"})
    public Team updateTeam(@PathVariable Integer teamId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String description,
                           @RequestParam(required = false) Boolean isPublic,
                           Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username);
        Team team = teamService.findByKey(teamId);

        // checking if current user is admin of this team (only admin can modify team)
        if (teamService.hasAdmin(team, currentUser)) {
            if (name != null && !name.isEmpty())
                team.setName(name);
            if (description != null && !description.isEmpty())
                team.setDescription(name);
            if (isPublic != null)
                team.setPublic(isPublic);
            return teamService.saveOrUpdate(team);
        } else
            return null; // TODO: throw exception
    }

    // deleteByKey specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable Integer teamId, Principal principal) {
        String username = ((CustomUserDetails) ((OAuth2Authentication) principal).getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username);
        Team team = teamService.findByKey(teamId);

        // checking if current user is admin of this team (only admin can modify team)
        if (teamService.hasAdmin(team, currentUser)) {
            teamService.deleteByKey(teamId);
        }
        // TODO: throw exception in else clause
    }
}
