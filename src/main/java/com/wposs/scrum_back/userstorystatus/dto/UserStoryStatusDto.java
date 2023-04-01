package com.wposs.scrum_back.userstorystatus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStoryStatusDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userStoryStateId;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String userStoryStatusName;

    public Long getUserStoryStateId() {
        return userStoryStateId;
    }

    public void setUserStoryStateId(Long userStoryStateId) {
        this.userStoryStateId = userStoryStateId;
    }

    public String getUserStoryStatusName() {
        return userStoryStatusName;
    }

    public void setUserStoryStatusName(String userStoryStatusName) {
        this.userStoryStatusName = userStoryStatusName;
    }
}
