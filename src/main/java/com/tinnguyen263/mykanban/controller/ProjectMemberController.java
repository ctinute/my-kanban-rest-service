package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.ProjectMemberDto;
import com.tinnguyen263.mykanban.exceptions.NoViewPermissionException;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.model.ProjectMember;
import com.tinnguyen263.mykanban.model.ProjectMemberPK;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.service.ProjectMemberService;
import com.tinnguyen263.mykanban.service.ProjectService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "api/projects/{projectId}/members")
public class ProjectMemberController {


    private final ProjectService projectService;

    private final ProjectMemberService projectMemberService;

    private final UserService userService;

    @Autowired
    public ProjectMemberController(ProjectService projectService, ProjectMemberService projectMemberService, UserService userService) {
        this.projectService = projectService;
        this.projectMemberService = projectMemberService;
        this.userService = userService;
    }


    //get members of  project
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<ProjectMemberDto> getMembersOfProject(@PathVariable Integer projectId,
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

    // add member to project
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addMemberToProject(@PathVariable Integer projectId,
                                   @RequestBody ProjectMemberDto projectMemberDto) {
        projectMemberService.saveOrUpdate(toEntity(projectMemberDto, projectId));
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.PUT)
    public void changeMemberRole(@PathVariable Integer projectId,
                                 @PathVariable Integer memberId,
                                 @RequestBody boolean isAdmin) {
        projectMemberService.saveOrUpdate(toEntity(new ProjectMemberDto(memberId, true), projectId));
    }
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void changeMemberRole(@PathVariable Integer projectId,
                                 @RequestBody ProjectMemberDto  projectMemberDto) {
        projectMemberService.saveOrUpdate(toEntity(projectMemberDto, projectId));
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.DELETE)
    public void deleteMember(@PathVariable Integer projectId,
                             @PathVariable Integer memberId) {
        projectMemberService.deleteByKey(new ProjectMemberPK(projectId, memberId));
    }


    private ProjectMember toEntity(ProjectMemberDto projectMemberDto, Integer projectId) {
        return new ProjectMember(projectService.findByKey(projectId), userService.findByKey(projectMemberDto.getMemberId()), projectMemberDto.getAdmin());
    }
}
