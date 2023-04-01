package com.wposs.scrum_back.taskteam.service;

import com.wposs.scrum_back.taskteam.entity.TaskTeam;
import com.wposs.scrum_back.taskteam.repository.TaskTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskTeamService {

private final TaskTeamRepository taskTeamRepository;

    public TaskTeamService(TaskTeamRepository taskTeamRepository) {
        this.taskTeamRepository = taskTeamRepository;
    }

    public List<TaskTeam> getAll(){return taskTeamRepository.findAll();}

    public Optional<TaskTeam> getTaskTeam(UUID taskTeamId){
        return taskTeamRepository.findById(taskTeamId);
    }
    public List<TaskTeam> getByTeam(UUID teamId){
        return taskTeamRepository.findByTeamId(teamId);
    }

    public TaskTeam update(UUID taskTeamId, TaskTeam taskTeam){
       return taskTeamRepository.findById(taskTeamId).map(taskTeam1 -> {
            taskTeam1.setTaskTeamName((taskTeam.getTaskTeamName()!=null)? taskTeam.getTaskTeamName() : taskTeam1.getTaskTeamName());
            return  taskTeamRepository.save(taskTeam);
        }).orElse(null);
    }

    public TaskTeam save(TaskTeam taskTeamDto){
        return taskTeamRepository.save(taskTeamDto);
    }

}
