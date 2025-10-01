package com.example.ModelMapperTest.ModelMapperFeatures.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// For Custom Conversion
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private LocalDate eventDate;

}
