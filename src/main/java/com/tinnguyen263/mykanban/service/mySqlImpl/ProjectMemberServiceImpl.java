package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.ProjectMember;
import com.tinnguyen263.mykanban.model.ProjectMemberPK;
import com.tinnguyen263.mykanban.repository.ProjectMemberRepository;
import com.tinnguyen263.mykanban.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    @Autowired
    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    @Override
    public List<ProjectMember> findAll() {
        return projectMemberRepository.findAll();
    }

    @Override
    public ProjectMember findByKey(ProjectMemberPK k) {
        return projectMemberRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(ProjectMemberPK o) {
        return findByKey(o) != null;
    }

    @Override
    public ProjectMember saveOrUpdate(ProjectMember o) {
        return projectMemberRepository.save(o);
    }

    @Override
    public void deleteByKey(ProjectMemberPK k) {
        projectMemberRepository.delete(k);
    }


    /*
    * OTHER METHODS
    * */

    @Override
    public boolean checkIfUserIsAdmin(Integer projectId, Integer userId) {
        ProjectMember p = findByKey(new ProjectMemberPK(projectId, userId));
        if (p != null)
            return p.getAdmin();
        return false;
    }

    @Override
    public boolean checkIfUserIsMember(Integer projectId, Integer userId) {
        return checkExisted(new ProjectMemberPK(projectId, userId));
    }
}
