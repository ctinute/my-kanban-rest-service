package com.tinnguyen263.mykanban.repository;

import com.tinnguyen263.mykanban.model.TeamUser;
import com.tinnguyen263.mykanban.model.TeamUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserPK> {
    List<TeamUser> findAllByTeamUserPK_UserId(Integer userId);
    List<TeamUser> findAllByTeamUserPK_TeamId(Integer teamId);
    TeamUser findDistinctByTeamUserPK(TeamUserPK teamUserPK);
}
