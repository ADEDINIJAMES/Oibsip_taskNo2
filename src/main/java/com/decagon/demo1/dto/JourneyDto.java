package com.decagon.demo1.dto;

import com.decagon.demo1.enums.Classes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JourneyDto {
        private Long id;
        private String journeyReference;
        private String trainName;
        private String trainNo;
        private String departure;
        private LocalDate arrivalDate;
        private LocalTime arrivalTime;
        private LocalDate departureDate;
        private LocalTime departureTime;
        private long duration;
        private String arrival;
        private BigDecimal fare;
        private Classes category;
}