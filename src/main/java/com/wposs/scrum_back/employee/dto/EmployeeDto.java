package com.wposs.scrum_back.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wposs.scrum_back.task.entity.Task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    @JsonProperty(value ="employee_id")
    private UUID employeeId;

    @JsonProperty(value = "employee_name")
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String employeeName;

    @JsonProperty(value = "employee_charge")
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String employeeCharge;

    @JsonProperty(value = "employee_email")
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String employeeEmail;

    @JsonProperty(value = "employee_knowledge")
    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String employeeKnowledge;

    private List<Task> tasks;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCharge() {
        return employeeCharge;
    }

    public void setEmployeeCharge(String employeeCharge) {
        this.employeeCharge = employeeCharge;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeKnowledge() {
        return employeeKnowledge;
    }

    public void setEmployeeKnowledge(String employeeKnowledge) {
        this.employeeKnowledge = employeeKnowledge;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
