package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.ProjectDto;
import com.tinnguyen263.mykanban.controller.dtos.ProjectMemberDto;
import com.tinnguyen263.mykanban.controller.dtos.ProjectMemberProjectWithTeamInfoDto;
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

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    // get current user's projects
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<ProjectMemberProjectWithTeamInfoDto> getCurrentUsersProjects(Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Collection<ProjectMember> projectMembers = currentUser.getProjectMembers();
        Collection<ProjectMemberProjectWithTeamInfoDto> memberProjectDtos = new ArrayList<>();
        for (ProjectMember pm : projectMembers)
            memberProjectDtos.add(new ProjectMemberProjectWithTeamInfoDto(projectService.findByKey(pm.getProjectMemberPK().getProjectId()), pm.getAdmin()));
        return memberProjectDtos;
    }

    // create new project
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ProjectDto addProject(@RequestBody ProjectDto project, Principal principal) {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        // TODO: turn these command into 1 transaction
        Project newProject = projectService.saveOrUpdate(toEntity(project));
        ProjectMember projectMember = new ProjectMember(newProject, currentUser, true);
        projectMemberService.saveOrUpdate(projectMember);

        return new ProjectDto(newProject);
    }


    // get specific project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ProjectDto getSpecificProject(@PathVariable Integer projectId, Principal principal) throws NoViewPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);

        // deny if project is not public or user is not a member of this project
        if (!project.getIsPublic() && !projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoViewPermissionException();
        }

        return new ProjectDto(project);
    }

    // update specific project
    @RequestMapping(value = "/{projectId", method = RequestMethod.PUT)
    public ProjectDto updateSpecificProject(@PathVariable Integer projectId,
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
        oldProject.setIsPublic(project.getPublic());
        oldProject.setTeam(teamService.findByKey(project.getTeamId()));

        return new ProjectDto(projectService.saveOrUpdate(oldProject));
    }

    // delete specific project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public void deleteSpecificProject(@PathVariable Integer projectId,
                                      Principal principal) throws NoModifyPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);

        // deny if user is not a admin of this project
        if (!projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoModifyPermissionException();
        }

        projectService.deleteByKey(projectId);
    }


    //get members of specific project
    @RequestMapping(value = "/{projectId}/members", method = RequestMethod.GET)
    public Collection<ProjectMemberDto> getMembersOfSpecificProject(@PathVariable Integer projectId,
                                                                    Principal principal) throws NoViewPermissionException {
        User currentUser = Utils.getCurrentUserFromPrincipal(principal, userService);
        Project project = projectService.findByKey(projectId);

        // deny if project is not public or user is not a member of this project
        if (!project.getIsPublic() && !projectMemberService.checkIfUserIsMember(projectId, currentUser.getId())) {
            throw new NoViewPermissionException();
        }

        Collection<ProjectMember> projectMembers = project.getProjectMembers();
        Collection<ProjectMemberDto> memberDtos = new ArrayList<>();
        for (ProjectMember pm : projectMembers)
            memberDtos.add(new ProjectMemberDto(pm));
        return memberDtos;
    }


    // DTOs

    public Project toEntity(ProjectDto projectDto) {
        Project p = new Project();
        if (projectDto.getId() != null) {
            Project sp = projectService.findByKey(projectDto.getId());
            if (sp != null)
                p = sp;
        }
        p.setName(projectDto.getName());
        p.setDescription(projectDto.getDescription());
        p.setIsPublic(projectDto.getPublic());
        if (projectDto.getTeamId() != null)
            p.setTeam(teamService.findByKey(projectDto.getTeamId()));
        else
            p.setTeam(null);
//            Collection<ProjectMember> projectMembers = new ArrayList<>();
//            for (MemberDto pm : members)
//                projectMembers.add(pm.toEntity(id));
//            p.setProjectMembers(projectMembers);
        return p;
    }

    public ProjectMember toEntity(ProjectMemberDto projectMemberDto, Integer projectId) {
        return new ProjectMember(projectService.findByKey(projectId), userService.findByKey(projectMemberDto.getMemberId()), projectMemberDto.getAdmin());
    }
}
