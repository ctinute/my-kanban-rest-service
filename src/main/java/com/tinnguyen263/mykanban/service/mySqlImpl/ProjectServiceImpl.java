package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.repository.ProjectRepository;
import com.tinnguyen263.mykanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findByKey(Integer k) {
        return projectRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(Integer o) {
        return projectRepository.queryDistinctFirstById(o);
    }

    @Override
    public Project saveOrUpdate(Project o) {
        return projectRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        projectRepository.delete(k);
    }
}
