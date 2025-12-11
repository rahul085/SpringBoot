package com.example.Registration.dto;

import lombok.Data;

import java.util.List;

@Data
public class StateDataWrapper {
    private List<StateRequestDto> states;
}
