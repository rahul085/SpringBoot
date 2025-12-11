package com.example.Registration.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class District {
    @Id
    @SequenceGenerator(name = "DIST_GEN",allocationSize = 1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "DIST_GEN")
    private Long districtId;
    private String districtName;
    @ManyToOne
    @JsonBackReference
    private State state;
    @OneToMany(mappedBy = "district",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Users> usersList;
}
