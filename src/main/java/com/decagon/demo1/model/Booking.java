package com.decagon.demo1.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Roles;
import com.decagon.demo1.dto.UserDto;
import com.decagon.demo1.enums.Classes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Journey journeyId;
   @OneToOne
    private Passenger passenger;
   private Classes classes;
   @ManyToOne
   private Users user;
   private BigDecimal userBalance = BigDecimal.valueOf(0);



}
