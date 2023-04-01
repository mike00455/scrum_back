package com.wposs.scrum_back.project.service;

import com.wposs.scrum_back.project.entity.Project;
import com.wposs.scrum_back.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public Optional<Project> finById(UUID projectId){
        return projectRepository.findById(projectId);
    }

    public Project updateProject(UUID projectId, Project project){
        return projectRepository.findById(projectId).map(project1 -> {
            project1.setProjectName((project.getProjectName()!=null)? project.getProjectName() : project1.getProjectName());
            return projectRepository.save(project1);
        }).orElse(null);
    }

    public Boolean existProjectByName(String projectName){
        return projectRepository.existsByProjectName(projectName);
    }

    public List<Project> getProjectsByAreaId(UUID areaId){
        return projectRepository.getByAreaId(areaId);
    }
}
