package com.tinnguyen263.mykanban.service;

public interface AuthorizationService {
    public boolean userCanModifyProject(String username, Integer projectId);

    public boolean userCanAccessProject(String username, Integer projectId);
}
