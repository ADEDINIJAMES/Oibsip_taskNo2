package com.decagon.demo1.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Roles;
import com.decagon.demo1.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String fullName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private BigDecimal balance;
    private String address;
    private String title;
    private String phone;





    public Users(UserDto signedUpUser) {
    this. username= signedUpUser.getUsername();
    this.fullName = signedUpUser.getFullName();
    this.password=  BCrypt.withDefaults()
            .hashToString(12, signedUpUser.getPassword().toCharArray());
    this.role= Roles.valueOf(signedUpUser.getRole());
    this.address = signedUpUser.getAddress();
            this.phone = signedUpUser.getPhone();
            this.title = signedUpUser.getTitle();
            this.balance = signedUpUser.getBalance();


    }

}
