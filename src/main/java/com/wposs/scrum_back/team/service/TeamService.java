package com.wposs.scrum_back.team.service;

import com.wposs.scrum_back.team.entity.Team;
import com.wposs.scrum_back.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    public Team save(Team team){
        return teamRepository.save(team);
    }

    public Team findByUuid(UUID teamId){
        return teamRepository.findByTeamId(teamId);
    }

    public Optional<Team> finById(UUID teamId){
        return teamRepository.findById(teamId);
    }

    public Team updateTeam(UUID teamId, Team team){
        return teamRepository.findById(teamId).map(team1 -> {
            team1.setTeamName((team.getTeamName()!=null)? team.getTeamName() : team1.getTeamName());
            return teamRepository.save(team);
        }).orElse(null);
    }

    public Boolean existProjectByName(String teamName){
        return teamRepository.existsByTeamName(teamName);
    }

    public List<Team> getTeamsByAreaId(UUID areaId){
        return teamRepository.getByAreaId(areaId);
    }

}
