package com.wposs.scrum_back.subProject.service;

import com.wposs.scrum_back.subProject.entity.SubProject;
import com.wposs.scrum_back.subProject.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public List<SubProject> getAll(){
        return subProjectRepository.findAll();
    }

    public SubProject save(SubProject subProject){
        return subProjectRepository.save(subProject);
    }

    public Optional<SubProject> finById(UUID subProjectId){
        return subProjectRepository.findById(subProjectId);
    }

    public SubProject updateSubProject(UUID subProjectId, SubProject subProject){
        return subProjectRepository.findById(subProjectId).map(subProject1 -> {
            subProject1.setSubProjectName((subProject.getSubProjectName()!=null)? subProject.getSubProjectName() : subProject1.getSubProjectName());
            return subProjectRepository.save(subProject1);
        }).orElse(null);
    }

    public Boolean existSubProjectByName(String subProjectName){
        return subProjectRepository.existsBySubProjectName(subProjectName);
    }

    public List<SubProject> getSubProjectByProjectId(UUID projectId){
        return subProjectRepository.getByProjectId(projectId);
    }



}
