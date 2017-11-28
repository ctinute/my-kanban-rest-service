package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.ProjectDto;
import com.tinnguyen263.mykanban.controller.dtos.ProjectMemberProjectDto;
import com.tinnguyen263.mykanban.exceptions.NoModifyPermissionException;
import com.tinnguyen263.mykanban.exceptions.NoViewPermissionException;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.model.ProjectMember;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.ProjectMemberService;
import com.tinnguyen263.mykanban.service.ProjectService;
import com.tinnguyen263.mykanban.service.TeamService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMemberService projectMemberService;

    private final UserService userService;

    private final TeamService teamService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMemberService projectMemberService, UserService userService, TeamService teamService) {
        this.projectService = projectService;
        this.projectMemberService = projectMemberService;
        this.userService = userService;
        this.teamService = teamService;
    }

    // get current user's projects
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<ProjectMemberProjectDto> getMyProjects(Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        Collection<ProjectMember> projectMembers = currentUser.getProjectMembers();
        Collection<ProjectMemberProjectDto> projectDtos = new ArrayList<>();
        for (ProjectMember pm : projectMembers)
            projectDtos.add(new ProjectMemberProjectDto(pm.getProject(), pm.getAdmin()));
        return projectDtos;
    }


    // get project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ProjectDto getProject(@PathVariable Integer projectId, Principal principal) throws NoViewPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);

        // deny if project is not public or user is not a member of this project
        if (!project.getIsPublic() && !projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoViewPermissionException();
        }

        return new ProjectDto(project);
    }

    // create new project
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ProjectDto addProject(@RequestBody ProjectDto project,
                                 Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        Project newProject = projectService.saveOrUpdate(toEntity(project));
        ProjectMember projectMember = new ProjectMember(newProject, currentUser, true);
        projectMemberService.saveOrUpdate(projectMember);

        return new ProjectDto(newProject);
    }

    // update  project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public ProjectDto updateProject(@PathVariable Integer projectId,
                                            @RequestBody ProjectDto project,
                                            Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project oldProject = projectService.findByKey(projectId);

        // deny if user is not a admin of this project
        if (!projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoModifyPermissionException();
        }

        // ONLY update basic fields
        oldProject.setName(project.getName());
        oldProject.setDescription(project.getDescription());
        oldProject.setIsPublic(project.isPublic());
        oldProject.setTeam(teamService.findByKey(projectId));

        return new ProjectDto(projectService.saveOrUpdate(oldProject));
    }

    // delete  project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable Integer projectId,
                                      Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        // deny if user is not a admin of this project
        if (!projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoModifyPermissionException();
        }

        projectService.deleteByKey(projectId);  // TODO: not working, don't know why =_=!
    }

    // DTOs
    private Project toEntity(ProjectDto projectDto) {
        Project p = new Project();
        if (projectDto.getId() != null) {
            Project sp = projectService.findByKey(projectDto.getId());
            if (sp != null)
                p = sp;
        }
        p.setName(projectDto.getName());
        p.setDescription(projectDto.getDescription());
        p.setIsPublic(projectDto.isPublic());
        if (projectDto.getTeamId() != null)
            p.setTeam(teamService.findByKey(projectDto.getTeamId()));
        else
            p.setTeam(null);
        return p;
    }
}
