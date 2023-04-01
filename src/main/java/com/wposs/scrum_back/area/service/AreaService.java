package com.wposs.scrum_back.area.service;

import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.area.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AreaService {

    private final AreaRepository areaRepository;


    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<Area> getAll(){
        return areaRepository.findAll();
    }

    public Area save(Area area){
        return areaRepository.save(area);
    }

    public Optional<Area> findById(UUID areaId){
        return areaRepository.findById(areaId);
    }

    public Area finByUuid(UUID areaId){
        return areaRepository.findByAreaId(areaId);
    }

    public Area updateArea(UUID id, Area area){
        return areaRepository.findById(id).map(area1 -> {
            area1.setAreaName((area.getAreaName()!=null)? area.getAreaName() : area1.getAreaName());
            return areaRepository.save(area1);
        }).orElse(null);
    }

    public Boolean existAreaByName(String areaName){
        return areaRepository.existsByAreaName(areaName);
    }

}
