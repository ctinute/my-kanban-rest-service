package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Team;
import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.TeamUserPK;
import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.repository.TeamRepository;
import com.tinnguyen263.mykanban.repository.TeamUserRepository;
import com.tinnguyen263.mykanban.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    // TODO: remove usage of TeamUserRepository in TeamService
    private final TeamUserRepository teamUserRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamUserRepository teamUserRepository) {
        this.teamRepository = teamRepository;
        this.teamUserRepository = teamUserRepository;
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
    public Team saveOrUpdate(Team o) {
        return teamRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        teamRepository.delete(k);
    }

    @Override
    public TeamUser addMember(Team team, User user, boolean isAdmin) {
        return teamUserRepository.save(new TeamUser(team, user, isAdmin));
    }

    @Override
    public boolean hasMember(Team team, User user) {
        return teamUserRepository.findDistinctByTeamUserPK(new TeamUserPK(team.getId(), user.getId())) != null;
    }

    @Override
    public boolean hasAdmin(Team team, User user) {
        TeamUser t = teamUserRepository.findDistinctByTeamUserPK(new TeamUserPK(team.getId(), user.getId()));
        if (t != null)
            return t.getAdmin();
        else
            return false;
    }
}
