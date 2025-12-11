package com.example.Registration.controller;

import com.example.Registration.dto.StateDataWrapper;
import com.example.Registration.dto.StateResponseDto;
import com.example.Registration.service.StateService;
import com.example.Registration.service.WriteToExcelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {
    private final StateService service;
    private final WriteToExcelService writeToExcelService;

    public StateController(StateService service, WriteToExcelService writeToExcelService) {
        this.service = service;
        this.writeToExcelService = writeToExcelService;
    }

    @PostMapping
    public ResponseEntity<String> addStates(@RequestBody StateDataWrapper stateDataWrapper){
        return new ResponseEntity<>(service.saveAllStates(stateDataWrapper), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<StateResponseDto>> getAllStates(){
        return new ResponseEntity<>(service.getAllStates(),HttpStatus.OK);
    }

    @GetMapping("/generate-excel")
    public ResponseEntity<String> generateLocalFile(){
        return new ResponseEntity<>(writeToExcelService.generateExcelToClassPath(),HttpStatus.OK);
    }
}
