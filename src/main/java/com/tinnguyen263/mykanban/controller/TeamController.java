package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.exceptions.NoModifyPermissionException;
import com.tinnguyen263.mykanban.exceptions.NoViewPermissionException;
import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.TeamUserPK;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.TeamService;
import com.tinnguyen263.mykanban.service.TeamUserService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamUserService teamUserService;
    private final UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService, TeamUserService teamUserService) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamUserService = teamUserService;
    }


    /*
    * mapping "/api/teams"
    * */

    // get current user's teams
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public Collection<TeamUser> getMyTeams(Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        return currentUser.getTeamUsers();
    }

    // create new team
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json"})
    public Team addTeam(@RequestBody Team team,
                        Principal principal) {
        // save team and get id back
        Team newTeam = teamService.saveOrUpdate(team);

        // user who create team will be default team admin
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        TeamUser t = new TeamUser(newTeam, currentUser, true);
        teamUserService.saveOrUpdate(t);

        return newTeam;
    }


    /*
    * mapping "/api/teams/(teamId)
    * */

    // get specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET, produces = {"application/json"})
    public Team getTeam(@PathVariable Integer teamId,
                        Principal principal) throws NoViewPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);

        if (team.getPublic() || teamUserService.checkIfUserIsMember(teamId, currentUser.getId()))
            return team;
        else
            throw new NoViewPermissionException();

    }

    // update specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.PUT, produces = {"application/json"})
    public Team updateTeam(@PathVariable Integer teamId,
                           @RequestBody Team team,
                           Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team oldTeam = teamService.findByKey(teamId);

        if (teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId())) {
            if (team.getName() != null && !team.getName().isEmpty())
                oldTeam.setName(team.getName());
            if (team.getDescription() != null && !team.getDescription().isEmpty())
                oldTeam.setDescription(team.getDescription());
            oldTeam.setPublic(team.getPublic());
            return teamService.saveOrUpdate(oldTeam);
        } else
            throw new NoModifyPermissionException();
    }

    // deleteByKey specific team
    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable Integer teamId, Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);  // check if team existed, throws EntityNotFound

        // checking if current user is admin of this team (only admin can modify team)
        if (teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId())) {
            teamService.deleteByKey(teamId);
        } else
            throw new NoModifyPermissionException();
    }


    /*
    * mapping "/api/teams/{teamId}/users"
    * */

    // get members of a specific team
    @RequestMapping(value = "/{teamId}/users", method = RequestMethod.GET)
    public Collection<TeamUser> getMembers(@PathVariable Integer teamId, Principal principal) throws NoViewPermissionException {
        Team team = teamService.findByKey(teamId);

        // if team is public => everyone can view
        if (team.getPublic())
            return team.getTeamUsers();

        // else user must be member of team to view
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        if (teamUserService.checkIfUserIsMember(teamId, currentUser.getId())) {
            return team.getTeamUsers();
        }

        throw new NoViewPermissionException();
    }

    /*
    * mapping /api/teams/{teamId}/users/{userId}
    * */
    @RequestMapping(value = "/{teamId}/users/{userId}", method = RequestMethod.PUT)
    public void changeMemberRole(@PathVariable Integer teamId,
                                 @PathVariable Integer userId,
                                 @RequestBody boolean isAdmin,
                                 Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);
        User user = userService.findByKey(userId);

        if (!teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId()))
            throw new NoModifyPermissionException();

        TeamUser t = teamUserService.findByKey(new TeamUserPK(teamId, userId));
        t.setAdmin(isAdmin);
        teamUserService.saveOrUpdate(t);
    }

}
