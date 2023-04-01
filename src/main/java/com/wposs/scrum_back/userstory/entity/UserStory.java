package com.wposs.scrum_back.userstory.entity;

import com.wposs.scrum_back.subProject.entity.SubProject;
import com.wposs.scrum_back.userstorystatus.entity.UserStoryStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_story", schema = "wposs")
public class UserStory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_story_id")
    private UUID userStoryId;

    @Column(name = "user_story_name", length = 30)
    private String userStoryName;

    @Column(name = "user_story_score")
    private Integer userStoryScore;

    @Column(name = "user_story_archive")
    private String userStoryArchive;

    @Column(name = "user_story_status_id")
    private Long userStoryStateId;

    @Column(name = "sub_project_id", nullable = false)
    private UUID subProjectId;

    @ManyToOne
    @JoinColumn(name = "user_story_status_id", insertable = false, updatable = false)
    private UserStoryStatus userStoryState;

    @ManyToOne
    @JoinColumn(name = "sub_project_id", insertable = false, updatable = false)
    private SubProject subProject;


    public UUID getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(UUID userStoryId) {
        this.userStoryId = userStoryId;
    }

    public String getUserStoryName() {
        return userStoryName;
    }

    public void setUserStoryName(String userStoryName) {
        this.userStoryName = userStoryName;
    }

    public Integer getUserStoryScore() {
        return userStoryScore;
    }

    public void setUserStoryScore(Integer userStoryScore) {
        this.userStoryScore = userStoryScore;
    }

    public String getUserStoryArchive() {
        return userStoryArchive;
    }

    public void setUserStoryArchive(String userStoryArchive) {
        this.userStoryArchive = userStoryArchive;
    }

    public Long getUserStoryStateId() {
        return userStoryStateId;
    }

    public void setUserStoryStateId(Long userStoryStateId) {
        this.userStoryStateId = userStoryStateId;
    }

    public UUID getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(UUID subProjectId) {
        this.subProjectId = subProjectId;
    }

    public UserStoryStatus getUserStoryState() {
        return userStoryState;
    }

    public void setUserStoryState(UserStoryStatus userStoryState) {
        this.userStoryState = userStoryState;
    }

    public SubProject getSubProject() {
        return subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

}
