package com.wposs.scrum_back.project.controller;

import com.wposs.scrum_back.project.dto.ProjectDto;
import com.wposs.scrum_back.project.entity.Project;
import com.wposs.scrum_back.project.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    private final ModelMapper modelMapper;

    public ProjectController(ProjectService projectService, ModelMapper modelMapper) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get project by UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<ProjectDto> findById(@PathVariable UUID id){
        return projectService.finById(id).map(project -> new ResponseEntity<>(modelMapper.map(project, ProjectDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all projects")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<ProjectDto>> findAll(){
        List<Project> projects = projectService.getAll();
        return new ResponseEntity<>(projects.stream().map(project -> modelMapper.map(project,ProjectDto.class))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create project")
    @ApiResponses({@ApiResponse(code = 201, message = "project created"), @ApiResponse(code = 200, message = "project bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody ProjectDto projectDto){
        HashMap<String, String> map = new HashMap<>();
        if (projectService.existProjectByName(projectDto.getProjectName())){
            map.put("message", "Este nombre de proyecto ya existe");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Project project = projectService.save(modelMapper.map(projectDto, Project.class));
        return new ResponseEntity<>(modelMapper.map(project, ProjectDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the project")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated project"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateProject(@RequestBody Project project, @PathVariable("id") UUID projectId){
        Map<String, Object> map = new HashMap<>();
        map.put("message","Datos invalidos");
        if(projectService.finById(projectId).isPresent()){
            map.put("message", modelMapper.map(projectService.updateProject(projectId, project), ProjectDto.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    @GetMapping("/area/{areaId}")
    @ApiOperation("Get all projects by area id")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<ProjectDto>> findAllProjectsByAreaId(@PathVariable UUID areaId){
        List<Project> projects = projectService.getProjectsByAreaId(areaId);
        return new ResponseEntity<>(projects.stream().map(project -> modelMapper.map(project,ProjectDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
