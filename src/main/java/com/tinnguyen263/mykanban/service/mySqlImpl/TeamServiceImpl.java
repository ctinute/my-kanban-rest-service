package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.repository.TeamRepository;
import com.tinnguyen263.mykanban.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findByKey(Integer k) {
        return teamRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(Integer o) {
        return teamRepository.queryDistinctFirstById(o);
    }

    @Override
    public Team saveOrUpdate(Team o) {
        return teamRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        teamRepository.delete(k);
    }
}
