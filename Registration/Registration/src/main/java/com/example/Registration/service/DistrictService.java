package com.example.Registration.service;

import com.example.Registration.dto.DistrictResponseDto;
import com.example.Registration.dto.StateResponseDto;
import com.example.Registration.entity.District;
import com.example.Registration.repositories.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public List<DistrictResponseDto> getAllDistricts(){
        List<District> allDistricts = districtRepository.findAll();
        List<DistrictResponseDto> districtResponseList =new ArrayList<>();

        for(District district: allDistricts){
            DistrictResponseDto districtResponseDto=new DistrictResponseDto();
           districtResponseDto.setDistrictId(district.getDistrictId());
           districtResponseDto.setDistrictName(district.getDistrictName());
           districtResponseDto.setStateId(district.getState().getStateId());
           districtResponseList.add(districtResponseDto);

        }
        return districtResponseList;
    }
}
