package com.example.Registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistrictResponseDto {
    private Long districtId;
    private String districtName;
    private Long stateId;
}
