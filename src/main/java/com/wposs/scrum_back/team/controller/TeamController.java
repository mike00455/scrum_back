package com.wposs.scrum_back.team.controller;

import com.wposs.scrum_back.team.dto.TeamDto;
import com.wposs.scrum_back.team.entity.Team;
import com.wposs.scrum_back.team.service.TeamService;
import com.wposs.scrum_back.employee.dto.EmployeeDto;
import com.wposs.scrum_back.employee.entity.Employee;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    private final ModelMapper modelMapper;

    public TeamController(TeamService teamService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get team by UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<TeamDto> findById(@PathVariable UUID id){
        return teamService.finById(id).map(team -> new ResponseEntity<>(modelMapper.map(team, TeamDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all teams")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<TeamDto>> findAll(){
        List<Team> teams = teamService.getAll();
        return new ResponseEntity<>(teams.stream().map(team -> modelMapper.map(team,TeamDto.class))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create team")
    @ApiResponses({@ApiResponse(code = 201, message = "team created"), @ApiResponse(code = 200, message = "team bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody TeamDto teamDto){
        HashMap<String, String> map = new HashMap<>();
        if (teamService.existProjectByName(teamDto.getTeamName())){
            map.put("message", "Este nombre de equipo ya existe");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Team team = teamService.save(modelMapper.map(teamDto, Team.class));
        return new ResponseEntity<>(modelMapper.map(team, TeamDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the team")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated team"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateProject(@RequestBody Team team, @PathVariable("id") UUID teamId){
        Map<String, Object> map = new HashMap<>();
        map.put("message","Datos invalidos");
        if(teamService.finById(teamId).isPresent()){
            map.put("message", modelMapper.map(teamService.updateTeam(teamId, team), TeamDto.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/area/{areaId}")
    @ApiOperation("Get all teams by area id")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<TeamDto>> findAllProjectsByAreaId(@PathVariable UUID areaId){
        List<Team> teams = teamService.getTeamsByAreaId(areaId);
        return new ResponseEntity<>(teams.stream().map(team -> modelMapper.map(team,TeamDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/saveemployeeonteam/{id}")
    @ApiOperation("Update the area")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated area"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")})
    public ResponseEntity<TeamDto> updateTeamEmployeById(@Valid @RequestBody List<EmployeeDto> employeeDtos, @PathVariable("id") UUID id){
        Team team = this.teamService.findByUuid(id);
        List<Employee> employeeList = employeeDtos.stream()
                .map(employeeDto -> modelMapper.map(employeeDto, Employee.class)).collect(Collectors.toList());
        team.setEmployees(employeeList);
        Team teamUpdate = this.teamService.save(team);
        return  new ResponseEntity<>(modelMapper.map(teamUpdate, TeamDto.class), HttpStatus.OK);
    }
}
