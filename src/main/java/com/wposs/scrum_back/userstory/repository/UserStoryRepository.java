package com.wposs.scrum_back.userstory.repository;

import com.wposs.scrum_back.userstory.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, UUID> {

    UserStory findByUserStoryId(UUID userStoryId);

    List<UserStory> findBySubProjectId(UUID subProjectId);

}
