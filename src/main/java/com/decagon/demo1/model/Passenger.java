package com.decagon.demo1.model;

import com.decagon.demo1.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passenger {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    private String fullName;
    private Roles role;
    private BigDecimal balance;
    private String address;
    private String title;
    private String phone;
    @Column(unique = true)
    private String PNR;
    private String email;

}
