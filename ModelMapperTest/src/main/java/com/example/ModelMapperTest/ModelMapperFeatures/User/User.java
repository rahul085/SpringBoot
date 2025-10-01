package com.example.ModelMapperTest.ModelMapperFeatures.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// basic mapping
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String name;
}
