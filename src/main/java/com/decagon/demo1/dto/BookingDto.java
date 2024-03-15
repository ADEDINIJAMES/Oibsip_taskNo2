package com.decagon.demo1.dto;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Roles;
import com.decagon.demo1.enums.Classes;
import com.decagon.demo1.model.Booking;
import com.decagon.demo1.model.Journey;
import com.decagon.demo1.model.Passenger;
import com.decagon.demo1.model.Users;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private String journeyReference;
    private String PNR;
    private String passengerName;
    private String departure;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private long duration;
    private String arrival;
    private BigDecimal fare;
    private String category;
    private BigDecimal balance;



    public BookingDto(Booking booking) {
        this.id =booking.getId();
        this. journeyReference= booking.getJourneyId().getJourneyReference();
        this.passengerName = booking.getPassenger().getFullName();
        this.departure=  booking.getJourneyId().getDeparture();
        this.arrivalDate= booking.getJourneyId().getArrivalDate();
        this.arrivalTime = booking.getJourneyId().getArrivalTime();
        this.departureDate = booking.getJourneyId().getDepartureDate();
        this.departureTime = booking.getJourneyId().getDepartureTime();
        this.fare = booking.getJourneyId().getFare();
        this.arrival = booking.getJourneyId().getArrival();
        this.duration = booking.getJourneyId().getDuration();
        this.category = String.valueOf(booking.getClasses());
        this.balance = booking.getUserBalance();
        this.PNR = booking.getPassenger().getPNR();


    }


}
