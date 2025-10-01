package com.example.ModelMapperTest.ModelMapperFeatures.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// For Skipping Fields
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String username;
    private String password;
}
