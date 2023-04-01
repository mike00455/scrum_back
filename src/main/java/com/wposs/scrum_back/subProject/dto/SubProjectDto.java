package com.wposs.scrum_back.subProject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubProjectDto {

    @JsonProperty(value ="subProjectId", access = JsonProperty.Access.READ_ONLY)
    private UUID subProjectId;

    @JsonProperty(value = "subProjectName")
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String subProjectName;

    @JsonProperty(value = "projectId")
    private UUID projectId;

    public UUID getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(UUID subProjectId) {
        this.subProjectId = subProjectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
