package com.rahul.JwtPractise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "roles_app")
public class Roles {
    @Id
    @SequenceGenerator(name = "rolesSeq3")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rolesSeq3")
    private Long roleId;
    private String roleName;
}
