package com.decagon.demo1.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Repository.AdminRepository;
import com.decagon.demo1.Repository.JourneyRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.Roles;
import com.decagon.demo1.model.Journey;
import com.decagon.demo1.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CSVUtils {
    private UserRepository userRepository;
    private JourneyRepository productRepository;
    private AdminRepository adminRepository;

    @Autowired
    public CSVUtils(UserRepository userRepository, JourneyRepository productRepository, AdminRepository adminRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;

    }

    @PostConstruct
    public void readUserCSV() {
        List<Users> finAdmins = userRepository.findByRole(Roles.ADMIN);

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/mac/Documents/onlinereservation/src/main/java/com/decagon/demo1/admin.csv"))) {
                String line;
                boolean lineOne = false;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] user = line.split(",");
                    if (lineOne) {
                        Users users = Users.builder()
                                .username(user[0])
                                .fullName(user[1])
                                .password(BCrypt.withDefaults().hashToString(12, user[2].trim().toCharArray()))
                                .role(Roles.valueOf(user[3]))
                                .balance(new BigDecimal(500000))
                                .build();
                        if (finAdmins.isEmpty() || !(finAdmins.contains(users))) {
                            userRepository.save(users);
                        }


                    }
                    lineOne = true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


