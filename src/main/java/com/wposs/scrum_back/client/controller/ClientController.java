package com.wposs.scrum_back.client.controller;

import com.wposs.scrum_back.client.dto.ClientDto;
import com.wposs.scrum_back.client.entity.Client;
import com.wposs.scrum_back.client.service.ClientService;
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
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final ModelMapper modelMapper;

    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get client by UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<ClientDto> findById(@PathVariable UUID id){
        return clientService.findById(id).map(client -> new ResponseEntity<>(modelMapper.map(client, ClientDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @ApiOperation("Get all clients")
    @ApiResponses({@ApiResponse(code = 200, message = "success")})
    public ResponseEntity<List<ClientDto>> findAll(){
        List<Client> clients = clientService.getAll();
        return new ResponseEntity<>(clients.stream().map(client -> modelMapper.map(client,ClientDto.class))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping("/save/")
    @ApiOperation("Create client")
    @ApiResponses({@ApiResponse(code = 201, message = "client created"), @ApiResponse(code = 200, message = "client bad request")})
    public ResponseEntity<?> create(@Valid @RequestBody ClientDto clientDto){
        HashMap<String, String> map = new HashMap<>();
        if (clientService.existClientByName(clientDto.getClientName())){
            map.put("message", "Este nombre de cliente ya existe");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Client client = clientService.save(modelMapper.map(clientDto, Client.class));
        return new ResponseEntity<>(modelMapper.map(client, ClientDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the updated client"),
            @ApiResponse(code = 404, message = "Returns the data sent is invalid")
    })
    public ResponseEntity<Map<String, Object>> updateClient(@RequestBody Client client, @PathVariable("id") UUID clientId){
        Map<String, Object> map = new HashMap<>();
        map.put("message","Datos invalidos");
        if(clientService.findById(clientId).isPresent()){
            map.put("message", modelMapper.map(clientService.updateClient(clientId, client), ClientDto.class));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}