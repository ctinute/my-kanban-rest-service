package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.TeamUserPK;
import com.tinnguyen263.mykanban.repository.TeamUserRepository;
import com.tinnguyen263.mykanban.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamUserServiceImpl implements TeamUserService {

    private final TeamUserRepository teamUserRepository;

    @Autowired
    public TeamUserServiceImpl(TeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }


    @Override
    public List<TeamUser> findAll() {
        return teamUserRepository.findAll();
    }

    @Override
    public TeamUser findByKey(TeamUserPK k) {
        return teamUserRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(TeamUserPK o) {
        return teamUserRepository.queryDistinctFirstByTeamUserPK(o);
    }

    @Override
    public TeamUser saveOrUpdate(TeamUser o) {
        return teamUserRepository.save(o);
    }

    @Override
    public void deleteByKey(TeamUserPK k) {
        teamUserRepository.delete(k);
    }


    /*
    * OTHER METHODS
    * */

    @Override
    public boolean checkIfUserIsAdmin(Integer teamId, Integer userId) {
        return teamUserRepository.queryDistinctFirstByTeamUserPKAndIsAdmin(new TeamUserPK(teamId, userId), true);
    }

    @Override
    public boolean checkIfUserIsMember(Integer teamId, Integer userId) {
        return teamUserRepository.queryDistinctFirstByTeamUserPK(new TeamUserPK(teamId, userId));
    }

}
