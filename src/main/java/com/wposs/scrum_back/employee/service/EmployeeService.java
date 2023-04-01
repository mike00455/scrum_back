package com.wposs.scrum_back.employee.service;

import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.area.service.AreaService;
import com.wposs.scrum_back.employee.entity.Employee;
import com.wposs.scrum_back.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee updateEmployee(UUID id, Employee employee){
        return employeeRepository.findById(id).map(employee1 -> {
            employee1.setEmployeeName((employee.getEmployeeName()!=null)? employee.getEmployeeName() : employee1.getEmployeeName());
            employee1.setEmployeeCharge((employee.getEmployeeCharge()!=null)? employee.getEmployeeCharge() : employee1.getEmployeeCharge());
            employee1.setEmployeeEmail((employee.getEmployeeEmail()!=null)? employee.getEmployeeEmail() : employee1.getEmployeeEmail());
            employee1.setEmployeeKnowledge((employee.getEmployeeKnowledge()!=null)? employee.getEmployeeKnowledge() : employee1.getEmployeeKnowledge());
            return employeeRepository.save(employee1);
        }).orElse(null);
    }


    public List<Employee> getEmployeeByAreaId(UUID areaId){
        return employeeRepository.getAllByAreaId(areaId);
    }
    public Boolean existEmployeeByName(String employeeName){
        return employeeRepository.existsByEmployeeName(employeeName);
    }

    public Employee findByEmployeeId(UUID employeeId){
        return employeeRepository.findByEmployeeId(employeeId);
    }


}
