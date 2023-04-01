package com.wposs.scrum_back.team.entity;

import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.employee.entity.Employee;
import com.wposs.scrum_back.taskteam.entity.TaskTeam;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "team", schema = "wposs")
public class Team {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "team_id")
    private UUID teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "area_id")
    private UUID areaId;

    @ManyToOne
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "team_employee", schema = "wposs",
            joinColumns = @JoinColumn(name = "team_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "employee_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "employee_id"}, name = "uc_employee_team"))
    private List<Employee> employees;


    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public UUID getAreaId() {
        return areaId;
    }

    public void setAreaId(UUID areaId) {
        this.areaId = areaId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
