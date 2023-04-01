package com.wposs.scrum_back.userstory.controller;

import com.wposs.scrum_back.userstory.dto.UserStoryDto;
import com.wposs.scrum_back.userstory.entity.UserStory;
import com.wposs.scrum_back.userstory.service.UserStoryService;
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
@RequestMapping("/userstory")
public class UserStoryController {

    private final UserStoryService userStoryService;

    private final ModelMapper modelMapper;


    public UserStoryController(UserStoryService userStoryService, ModelMapper modelMapper) {
        this.userStoryService = userStoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{userStoryId}")
    @ApiOperation(value = "Get all User Story")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<UserStoryDto> finById(@PathVariable UUID userStoryId){
        return userStoryService.findById(userStoryId).map(userStory -> new ResponseEntity<>(modelMapper.map(userStory, UserStoryDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all User Stories")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<UserStoryDto>> findAll(){
        List<UserStory> userStories = userStoryService.getAll();
        return new ResponseEntity<>(userStories.stream().map(userStory  -> modelMapper.map(userStories,UserStoryDto.class))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create User Story")
    @ApiResponses({@ApiResponse(code = 201, message = "user story created"), @ApiResponse(code = 200, message = "user story bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody UserStoryDto userStoryDto){
        UserStory userStory = userStoryService.save(modelMapper.map(userStoryDto, UserStory.class));
        return new ResponseEntity<>(modelMapper.map(userStory, UserStoryDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the userStory")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated User story"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateUserStory(@RequestBody UserStoryDto userStoryDto, @PathVariable("id") UUID userStoryId){
        Map<String, Object> map = new HashMap<>();
        map.put("message","Datos invalidos");
        if(userStoryService.findById(userStoryId).isPresent()){
            map.put("message", modelMapper.map(userStoryService.updateUserStory(userStoryId, modelMapper.map(userStoryDto, UserStory.class)), UserStory.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/subproject/{subprojectId}")
    @ApiOperation("Get all user stories by subproject id")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<UserStoryDto>> findAllUserStoriesBySubProjectId(@PathVariable UUID subprojectId){
        List<UserStory> userStories = userStoryService.getUserStoriesBySubProjectId(subprojectId);
        return new ResponseEntity<>(userStories.stream().map(userStory -> modelMapper.map(userStories,UserStoryDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
