package com.rahul.JwtPractise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users_app")
public class Users {
    @Id
    @SequenceGenerator(name = "usersSeq3")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "usersSeq3")
    private Long userId;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(unique = true,nullable = false)
    private String password;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "App_Users_Roles",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Roles> roles=new HashSet<>();
}
