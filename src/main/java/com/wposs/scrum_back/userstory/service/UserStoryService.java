package com.wposs.scrum_back.userstory.service;

import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.userstory.entity.UserStory;
import com.wposs.scrum_back.userstory.repository.UserStoryRepository;
import org.springframework.stereotype.Service;

import javax.management.openmbean.OpenDataException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;

    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public List<UserStory> getAll(){
        return userStoryRepository.findAll();
    }

    public Optional<UserStory> findById(UUID userStoryId){
        return userStoryRepository.findById(userStoryId);
    }

    public UserStory getUserStoryByUuid(UUID userStoryId){
        return userStoryRepository.findByUserStoryId(userStoryId);
    }

    public List<UserStory> getUserStoriesBySubProjectId(UUID subProjectId){
        return userStoryRepository.findBySubProjectId(subProjectId);
    }

    public UserStory save(UserStory userStory){
        return userStoryRepository.save(userStory);
    }

    public UserStory updateUserStory(UUID userStoryId, UserStory userStory){
        return userStoryRepository.findById(userStoryId).map(userStory1 -> {
            userStory1.setUserStoryName((userStory.getUserStoryName() != null)? userStory.getUserStoryName(): userStory1.getUserStoryName());
            userStory1.setUserStoryArchive((userStory.getUserStoryArchive() != null)? userStory.getUserStoryArchive() : userStory1.getUserStoryArchive());
            userStory1.setUserStoryScore((userStory.getUserStoryScore() != null) ? userStory.getUserStoryScore() : userStory1.getUserStoryScore());
            userStory1.setUserStoryStateId((userStory.getUserStoryStateId() != null) ? userStory.getUserStoryStateId(): userStory1.getUserStoryStateId());
            userStory1.setSubProjectId((userStory.getSubProjectId() != null)? userStory.getSubProjectId() : userStory1.getSubProjectId());
            return userStoryRepository.save(userStory1);
        }).orElse(null);
    }
}
