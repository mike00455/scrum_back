package com.wposs.scrum_back.area.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wposs.scrum_back.team.dto.TeamDto;
import com.wposs.scrum_back.employee.dto.EmployeeDto;
import com.wposs.scrum_back.project.dto.ProjectDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDto {

    @JsonProperty(value ="areaId", access = JsonProperty.Access.READ_ONLY)
    private UUID areaId;

    @JsonProperty(value = "areaName")
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String areaName;

    @JsonIgnore
    private List<ProjectDto> projects;

    @JsonIgnore
    private List<TeamDto> teams;

    private List<EmployeeDto> employees;


    public UUID getAreaId() {
        return areaId;
    }

    public void setAreaId(UUID areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
