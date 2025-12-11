package com.example.Registration.controller;

import com.example.Registration.dto.DistrictResponseDto;
import com.example.Registration.service.DistrictService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {
    private final DistrictService service;

    public DistrictController(DistrictService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<DistrictResponseDto>> getAllDistricts(){
        return new ResponseEntity<>(service.getAllDistricts(), HttpStatus.OK);
    }
}
