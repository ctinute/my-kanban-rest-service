package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.TeamDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.exceptions.NoModifyPermissionException;
import com.tinnguyen263.mykanban.model.*;
import com.tinnguyen263.mykanban.service.ProjectMemberService;
import com.tinnguyen263.mykanban.service.ProjectService;
import com.tinnguyen263.mykanban.service.TeamService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(value = "api/projects/{projectId}/team")
public class ProjectTeamController {

    private final ProjectService projectService;

    private final ProjectMemberService projectMemberService;

    private final TeamService teamService;

    private final UserService userService;

    @Autowired
    public ProjectTeamController(ProjectService projectService, ProjectMemberService projectMemberService, TeamService teamService, UserService userService) {
        this.projectService = projectService;
        this.projectMemberService = projectMemberService;
        this.teamService = teamService;
        this.userService = userService;
    }

    //get team of  project
    @RequestMapping(value = "", method = RequestMethod.GET)
    public TeamDto getTeamOfProject(@PathVariable Integer projectId,
                                    Principal principal) throws NoAccessPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);

        // deny if project is not public or user is not a member of this project
        if (!project.getIsPublic() && !projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoAccessPermissionException();
        }

        return new TeamDto(project.getTeam());
    }

    //set team of  project
    @RequestMapping(value = "/{teamId}", method = RequestMethod.POST)
    public TeamDto setTeamOfProject(@PathVariable Integer projectId,
                                    @PathVariable Integer teamId,
                                    Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);
        Team team = teamService.findByKey(teamId);

        // deny if project is not public or user is not a member of this project
        if (!projectMemberService.checkIfUserIsAdmin(projectId, currentUser.getId())) {
            throw new NoModifyPermissionException();
        }

        // add all member of team to project
        Collection<TeamUser> teamUser = team.getTeamUsers();
        for (TeamUser tu : teamUser) {
            ProjectMember projectMember = new ProjectMember(project, tu.getUser(), false);
            if (!projectMemberService.checkExisted(projectMember.getProjectMemberPK()))
                projectMemberService.saveOrUpdate(projectMember);
        }

        return new TeamDto(project.getTeam());
    }

    // remove team
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void removeTeamOfProject(@PathVariable Integer projectId,
                                    @RequestParam boolean removeMember,
                                    Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);

        // deny if user is not an admin of this project
        if (!projectMemberService.checkIfUserIsAdmin(projectId, currentUser.getId())) {
            throw new NoModifyPermissionException();
        }

        // remove all team's member from project if requested
        Team team = project.getTeam();
        if (team != null && removeMember) {
            Collection<TeamUser> teamUser = team.getTeamUsers();
            for (TeamUser tu : teamUser) {
                ProjectMemberPK projectMemberPK = new ProjectMemberPK(projectId, tu.getTeamUserPK().getUserId());
                projectMemberService.deleteByKey(projectMemberPK);
            }
        }

        project.setTeam(null);
        projectService.saveOrUpdate(project);

        // leave current user as admin
        ProjectMember projectMember = new ProjectMember(project, currentUser, true);
        projectMemberService.saveOrUpdate(projectMember);
    }
}
