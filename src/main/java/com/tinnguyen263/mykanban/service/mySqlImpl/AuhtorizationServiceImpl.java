package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.ProjectMemberService;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuhtorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectMemberService projectMemberService;

    @Override
    public boolean userCanModifyProject(String username, Integer projectId) {
        Integer uid = userService.findByUsername(username).getId();
        return projectMemberService.checkIfUserIsAdmin(projectId, uid);
    }

    @Override
    public boolean userCanAccessProject(String username, Integer projectId) {
        Integer uid = userService.findByUsername(username).getId();
        return projectMemberService.checkIfUserIsAdmin(projectId, uid);
    }
}
