package com.example.Registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "REG_USERS")
public class Users {
    @Id
    @SequenceGenerator(name = "NEW_USER",allocationSize = 1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "NEW_USER")
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String address;
    private Integer pinCode;
    private Integer mobileNumber;
    private String email;
    private Integer otp;
    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;

}
