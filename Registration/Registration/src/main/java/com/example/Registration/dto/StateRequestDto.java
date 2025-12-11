package com.example.Registration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StateRequestDto {
    @JsonProperty("state")
    private String stateName;
    @JsonProperty("districts")
    private List<String> districtNames;
}
