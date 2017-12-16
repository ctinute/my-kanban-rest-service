package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuhtorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamUserService teamUserService;

    @Override
    public boolean userCanModifyProject(String username, int projectId) {
        int uid = userService.findByUsername(username).getId();
        return projectMemberService.checkIfUserIsAdmin(projectId, uid);
    }

    @Override
    public boolean userCanAccessProject(String username, int projectId) {
        int uid = userService.findByUsername(username).getId();
        Project project = projectService.findByKey(projectId);
        return (project.getIsPublic() || projectMemberService.checkIfUserIsMember(projectId, uid));
    }

    @Override
    public boolean userCanModifyTeam(String username, int teamId) {
        int uid = userService.findByUsername(username).getId();
        return teamUserService.checkIfUserIsAdmin(teamId, uid);
    }

    @Override
    public boolean userCanAccessTeam(String username, int teamId) {
        int uid = userService.findByUsername(username).getId();
        Team team = teamService.findByKey(teamId);
        return (team.getPublic() || teamUserService.checkIfUserIsMember(teamId, uid));
    }
}
