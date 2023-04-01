package com.wposs.scrum_back.subProject.repository;

import com.wposs.scrum_back.subProject.entity.SubProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubProjectRepository  extends JpaRepository<SubProject, UUID> {

    Boolean existsBySubProjectName(String subProjectName);

    List<SubProject> getByProjectId(UUID projectId);
}
