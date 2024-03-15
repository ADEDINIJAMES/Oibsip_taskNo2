package com.decagon.demo1.model;

import com.decagon.demo1.dto.JourneyDto;
import com.decagon.demo1.enums.Classes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String journeyReference;
    private String trainName;
    private String trainNo;
    private String departure;
    private String arrival;
    private BigDecimal fare;
    private Classes category;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private long duration;


    public Journey(JourneyDto journeyDto){
        this.category=journeyDto.getCategory();
        this.trainName = journeyDto.getTrainName();
        this.trainNo = journeyDto.getTrainNo();
        this.departure=journeyDto.getDeparture();
        this.arrival = journeyDto.getArrival();
        this.arrivalDate = journeyDto.getArrivalDate();
        this.arrivalTime = journeyDto.getArrivalTime();
        this.departureTime = journeyDto.getDepartureTime();
        this.departureDate=journeyDto.getDepartureDate();
        this.fare = journeyDto.getFare();
        this.duration = journeyDto.getDuration();
        this.journeyReference = journeyDto.getJourneyReference();

    }
}

