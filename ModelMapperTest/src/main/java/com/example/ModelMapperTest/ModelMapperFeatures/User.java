package com.example.ModelMapperTest.ModelMapperFeatures;


import com.example.ModelMapperTest.ModelMapperFeatures.Account.Account;
import com.example.ModelMapperTest.ModelMapperFeatures.Account.AccountDto;
import com.example.ModelMapperTest.ModelMapperFeatures.Employee.Employee;
import com.example.ModelMapperTest.ModelMapperFeatures.Employee.EmployeeDto;
import com.example.ModelMapperTest.ModelMapperFeatures.Event.Event;
import com.example.ModelMapperTest.ModelMapperFeatures.Event.EventDto;
import com.example.ModelMapperTest.ModelMapperFeatures.Student.Department;
import com.example.ModelMapperTest.ModelMapperFeatures.Student.Student;
import com.example.ModelMapperTest.ModelMapperFeatures.Student.StudentDto;
import com.example.ModelMapperTest.ModelMapperFeatures.User.User;
import com.example.ModelMapperTest.ModelMapperFeatures.User.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Configuration
class AppConfig{
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//    basic mapping
       // modelMapper.map(User.class, UserDto.class);

        // field mismatch mapping

        modelMapper.createTypeMap(Employee.class, EmployeeDto.class)
                .addMappings(mapper->{
                    mapper.map(Employee::getEmpName,EmployeeDto::setName);

                });

        // Flattening nested objects


        modelMapper.createTypeMap(Student.class, StudentDto.class)
                .addMappings(mapper->{
                    mapper.map(student->student.getDepartment().getName(),
                            StudentDto::setDepartmentName);
                });


        //converting one data type to the other

        Converter<LocalDate,String> localDateToString=context->
                context.getSource().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        modelMapper.addConverter(localDateToString);



        // Skip mapping

        modelMapper.createTypeMap(Account.class, AccountDto.class).addMappings(mapper->{
            mapper.skip(AccountDto::setPassword);
        });

        return modelMapper;



    }

}

@Component
@AllArgsConstructor
class DemoRunner implements CommandLineRunner{
    private ModelMapper modelMapper;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Basic mapping test....");
        User user=new User(1,"Rahul");
        UserDto dto=modelMapper.map(user, UserDto.class);

        System.out.println("User :"+user);
        System.out.println("UserDto "+dto);

        System.out.println("-----------------------------------------------");


        System.out.println("Field mismatch mapping test");
        Employee employee=new Employee("Rahul");
        EmployeeDto employeeDto=modelMapper.map(employee,EmployeeDto.class);


        System.out.println("Employee :"+employeeDto);
        System.out.println("EmployeeDto "+employeeDto);


        System.out.println("--------------------------------------------------");


        System.out.println("flattening nested object mapping test");
        Department department=new Department("CSE");
        Student student=new Student(department);
        StudentDto studentDto=modelMapper.map(student, StudentDto.class);

        System.out.println("Student :"+student.getDepartment());
        System.out.println("StudentDto :"+studentDto);


        System.out.println("------------------------------------------");

        Event event =new Event(LocalDate.of(2003,11,2));
        EventDto eventDto=modelMapper.map(event, EventDto.class);

        System.out.println("Event "+event);
        System.out.println("EventDto : "+eventDto);


        System.out.println("-------------------------------------------");


        System.out.println("Skip mapping test");
        Account account=new Account("Rahul","123");
        AccountDto accountDto=modelMapper.map(account,AccountDto.class);

        System.out.println("Account : "+account);
        System.out.println("AccountDto : "+accountDto);




    }
}



