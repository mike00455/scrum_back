package com.wposs.scrum_back.area.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wposs.scrum_back.team.entity.Team;
import com.wposs.scrum_back.employee.entity.Employee;
import com.wposs.scrum_back.project.entity.Project;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "area", schema = "wposs")
public class Area {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "area_id")
    private UUID areaId;


    @Column(name = "areaName", nullable = false, length = 100)
    private String areaName;
    @JsonIgnore
    @OneToMany(mappedBy = "area")
    private List<Project> projects;
    @JsonIgnore
    @OneToMany(mappedBy = "area")
    private List<Team> teams;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "employee_area", schema = "wposs",
            joinColumns = @JoinColumn(name = "area_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "employee_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "area_id"}, name = "uc_employee_area"))
    private List<Employee> employees;

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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
