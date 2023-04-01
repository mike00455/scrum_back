package com.wposs.scrum_back.subProject.controller;

import com.wposs.scrum_back.project.dto.ProjectDto;
import com.wposs.scrum_back.project.entity.Project;
import com.wposs.scrum_back.subProject.dto.SubProjectDto;
import com.wposs.scrum_back.subProject.entity.SubProject;
import com.wposs.scrum_back.subProject.service.SubProjectService;
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
@RequestMapping("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    private final ModelMapper modelMapper;

    public SubProjectController(SubProjectService subProjectService, ModelMapper modelMapper) {
        this.subProjectService = subProjectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get subproject by UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<SubProjectDto> findById(@PathVariable UUID id){
        return subProjectService.finById(id).map(subProject -> new ResponseEntity<>(modelMapper.map(subProject, SubProjectDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all subprojects")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<SubProjectDto>> findAll(){
        List<SubProject> subProjects = subProjectService.getAll();
        return new ResponseEntity<>(subProjects.stream().map(subProject  -> modelMapper.map(subProject,SubProjectDto.class))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create subproject")
    @ApiResponses({@ApiResponse(code = 201, message = "subproject created"), @ApiResponse(code = 200, message = "subproject bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody SubProjectDto subProjectDto){
        HashMap<String, String> map = new HashMap<>();
        if (subProjectService.existSubProjectByName(subProjectDto.getSubProjectName())){
            map.put("message", "Este nombre de subproyecto ya existe");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        SubProject subProject = subProjectService.save(modelMapper.map(subProjectDto, SubProject.class));
        return new ResponseEntity<>(modelMapper.map(subProject, ProjectDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the subproject")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated subproject"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateSubProject(@RequestBody SubProject subProject, @PathVariable("id") UUID subProjectId){
        Map<String, Object> map = new HashMap<>();
        map.put("message","Datos invalidos");
        if(subProjectService.finById(subProjectId).isPresent()){
            map.put("message", modelMapper.map(subProjectService.updateSubProject(subProjectId, subProject), SubProjectDto.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/project/{projectId}")
    @ApiOperation("Get all subprojects by project id")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<SubProjectDto>> findAllSubProjectsByProjectId(@PathVariable UUID projectId){
        List<SubProject> subProjects = subProjectService.getSubProjectByProjectId(projectId);
        return new ResponseEntity<>(subProjects.stream().map(subProject -> modelMapper.map(subProject,SubProjectDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

}
