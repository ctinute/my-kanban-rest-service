package com.tinnguyen263.mykanban.service;

public interface AuthorizationService {
    public boolean userCanModifyProject(String username, int projectId);

    public boolean userCanAccessProject(String username, int projectId);

    public boolean userCanModifyTeam(String username, int teamId);

    public boolean userCanAccessTeam(String username, int teamId);
}
