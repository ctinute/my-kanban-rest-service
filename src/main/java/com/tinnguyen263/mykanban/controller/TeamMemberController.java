package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.NewTeamMemberDto;
import com.tinnguyen263.mykanban.controller.dtos.TeamUserUserDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.exceptions.NoModifyPermissionException;
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
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "api/teams/{teamId}/members")
public class TeamMemberController {

    private final TeamService teamService;
    private final TeamUserService teamUserService;
    private final UserService userService;

    @Autowired
    public TeamMemberController(TeamService teamService, UserService userService, TeamUserService teamUserService) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamUserService = teamUserService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<TeamUserUserDto> getMembers(@PathVariable Integer teamId, Principal principal) throws NoAccessPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);

        if (!team.getPublic() && !teamUserService.checkIfUserIsMember(teamId, currentUser.getId()))
            throw new NoAccessPermissionException();

        Collection<TeamUser> teamUsers = team.getTeamUsers();
        Collection<TeamUserUserDto> teamUserUserDtos = new ArrayList<>();
        for (TeamUser tu : teamUsers)
            teamUserUserDtos.add(new TeamUserUserDto(tu.getUser(), tu.getAdmin()));
        return teamUserUserDtos;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addMember(@PathVariable Integer teamId,
                          @RequestBody NewTeamMemberDto newMemberDto,
                          Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);
        User member = userService.findByKey(newMemberDto.getMemberId());

        if (!teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId()))
            throw new NoModifyPermissionException();

        teamUserService.saveOrUpdate(new TeamUser(team, member, newMemberDto.isAdmin()));
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.PUT)
    public void updateMemberRole(@PathVariable Integer teamId,
                                 @RequestBody NewTeamMemberDto newMemberDto,
                                 Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);
        User member = userService.findByKey(newMemberDto.getMemberId());

        if (!teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId()))
            throw new NoModifyPermissionException();

        TeamUser t = teamUserService.findByKey(new TeamUserPK(teamId, member.getId()));
        t.setAdmin(newMemberDto.isAdmin());
        teamUserService.saveOrUpdate(t);
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.DELETE)
    public void deleteMember(@PathVariable Integer teamId,
                             @PathVariable Integer memberId,
                             Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Team team = teamService.findByKey(teamId);
        User user = userService.findByKey(memberId);

        if (!teamUserService.checkIfUserIsAdmin(teamId, currentUser.getId()))
            throw new NoModifyPermissionException();

        teamUserService.deleteByKey(new TeamUserPK(teamId, memberId));
    }
}
