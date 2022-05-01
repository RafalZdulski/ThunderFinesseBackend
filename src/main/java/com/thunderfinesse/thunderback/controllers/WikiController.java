package com.thunderfinesse.thunderback.controllers;

import com.thunderfinesse.thunderback.data.VehicleBase;
import com.thunderfinesse.thunderback.services.WikiService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wiki")
@CrossOrigin(origins = "http://localhost:4200")
public class WikiController {

    @GetMapping("/all")
    public List<VehicleBase> getSummary(){
        WikiService wikiService = new WikiService();
        return wikiService.getAllVehicles();
    }



}
