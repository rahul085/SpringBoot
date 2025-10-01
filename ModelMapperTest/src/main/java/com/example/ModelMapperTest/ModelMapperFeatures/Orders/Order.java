package com.example.ModelMapperTest.ModelMapperFeatures.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long id;
    private LocalDateTime orderDate;
    private double price;
    private int quantity;
}


