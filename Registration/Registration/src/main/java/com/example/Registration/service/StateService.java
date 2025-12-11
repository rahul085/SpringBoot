package com.example.Registration.service;

import com.example.Registration.dto.DistrictResponseDto;
import com.example.Registration.dto.StateDataWrapper;
import com.example.Registration.dto.StateRequestDto;
import com.example.Registration.dto.StateResponseDto;
import com.example.Registration.entity.District;
import com.example.Registration.entity.State;
import com.example.Registration.repositories.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }


@Transactional
public String saveAllStates(StateDataWrapper stateDataWrapper){
            for(StateRequestDto dto:stateDataWrapper.getStates()){
                State state=new State();
                state.setStateName(dto.getStateName());

                Set<District> districtSet=new HashSet<>();
                if(dto.getDistrictNames()!=null){
                    for(String districtName: dto.getDistrictNames()){
                        District district=new District();
                        district.setDistrictName(districtName);
                        district.setState(state);
                        districtSet.add(district);
                    }
                }
                state.setDistrictSet(districtSet);
                stateRepository.save(state);


            }

            return "Successfully saved "+stateDataWrapper.getStates().size()+" states and districts";
}

public List<StateResponseDto> getAllStates(){
    List<State> allStates = stateRepository.findAll();
    List<StateResponseDto> stateList=new ArrayList<>();

    for(State state: allStates){
        StateResponseDto stateResponseDto=new StateResponseDto();
        stateResponseDto.setStateId(state.getStateId());
        stateResponseDto.setStateName(state.getStateName());
        stateResponseDto.setDistrictIds(state.getDistrictSet().stream()
                .map(district -> district.getDistrictId())
                .collect(Collectors.toSet()));
        stateList.add(stateResponseDto);
    }
    return stateList;
}

}
