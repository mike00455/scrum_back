package com.wposs.scrum_back.employee.controller;

import com.wposs.scrum_back.area.dto.AreaDto;
import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.employee.dto.EmployeeDto;
import com.wposs.scrum_back.employee.entity.Employee;
import com.wposs.scrum_back.employee.service.EmployeeService;
import com.wposs.scrum_back.taskteam.dto.TaskTeamDto;
import com.wposs.scrum_back.taskteam.entity.TaskTeam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get employee by UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<EmployeeDto> findById(@PathVariable UUID id) {
        return employeeService.findById(id).map(employee -> new ResponseEntity<>(modelMapper.map(employee, EmployeeDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all employees")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<EmployeeDto>> findAll() {
        List<Employee> employees = employeeService.getAll();
        return new ResponseEntity<>(employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/save/")
    @ApiOperation("Create employee")
    @ApiResponses({@ApiResponse(code = 201, message = "employee created"), @ApiResponse(code = 200, message = "employee bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.save(modelMapper.map(employeeDto, Employee.class));
        return new ResponseEntity<>(modelMapper.map(employee, EmployeeDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the employee")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated employee"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateEmployee(@RequestBody Employee employee, @PathVariable("id") UUID employeeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Datos invalidos");
        if (employeeService.findById(employeeId).isPresent()) {
            map.put("message", modelMapper.map(employeeService.updateEmployee(employeeId, employee), EmployeeDto.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

//    @PutMapping("/savetaskonemployee/{employeeId}")
//    public ResponseEntity<EmployeeDto> updateTaskEmployee(@Valid @RequestBody List<TaskDto> taskDtos, @PathVariable("employeeId") UUID employeeId){
//        Employee employee = this.employeeService.findByEmployeeId(employeeId);
//        List<TaskTeam> tasks = taskDtos.stream()
//                .map(taskDto -> modelMapper.map(taskDto, Task.class)).collect(Collectors.toList());
//        employee.setTasks(tasks);
//        Employee employeeUpdate = this.employeeService.save(employee);
//        return  new ResponseEntity<>(modelMapper.map(employeeUpdate, EmployeeDto.class), HttpStatus.OK);
//    }

}
