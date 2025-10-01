package com.example.ModelMapperTest.ModelMapperFeatures.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;
    private String dateString;
    private double price;
    private int quantity;
    private double totalCost;
}
