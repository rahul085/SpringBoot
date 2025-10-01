package com.example.EmployeeManagement.Config;

import com.example.EmployeeManagement.Dto.EmployeeResponseDto;
import com.example.EmployeeManagement.Entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
//        ModelMapper modelMapper=new ModelMapper();
//        modelMapper.createTypeMap(Employee.class, EmployeeResponseDto.class).
//                addMappings(mapper->{
//                    mapper.map(Employee::getEmpName,EmployeeResponseDto::setName);
//                });

        return new ModelMapper();
    }
}
