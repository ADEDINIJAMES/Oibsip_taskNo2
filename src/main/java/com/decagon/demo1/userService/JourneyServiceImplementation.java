package com.decagon.demo1.userService;

import com.decagon.demo1.Repository.BookingRepository;
import com.decagon.demo1.Repository.JourneyRepository;
import com.decagon.demo1.Repository.PassengerRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.dto.JourneyDto;
import com.decagon.demo1.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class JourneyServiceImplementation {
    private JourneyRepository journeyRepository;
    private UserRepository userRepository;
    private PassengerRepository passengerRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public JourneyServiceImplementation(JourneyRepository journeyRepository, UserRepository userRepository, PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.journeyRepository = journeyRepository;
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    public Function<JourneyDto, Journey> saveProduct = (journeyDto) -> {

        journeyDto.setArrivalDate(calculateArrivalDate(journeyDto.getDepartureDate(), journeyDto.getDuration()));
        journeyDto.setArrivalTime(journeyDto.getDepartureTime().plusMinutes(journeyDto.getDuration()));
        journeyDto.setJourneyReference(generateRef("JOR"));
        journeyDto.setTrainNo(generateRef("TR"));
        return journeyRepository.save(new Journey(journeyDto));

    };



    public LocalDate calculateArrivalDate(LocalDate departureDate, long durationMinutes) {
        long days = durationMinutes / (24 * 60);
        long remainingMinutes = durationMinutes % (24 * 60);
        LocalDate arrivalDate = departureDate.plusDays(days);
        if (remainingMinutes > 0) {
            LocalTime departureTime = LocalTime.of(0, 0);
            LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
            LocalDateTime arrivalDateTime = departureDateTime.plusMinutes(remainingMinutes);
            arrivalDate = arrivalDateTime.toLocalDate();
        }
        return arrivalDate;
    }

    public void deleteJourney(Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Journey product = new Journey();
        journeyRepository.deleteById(id);
    }


    public Supplier<List<Journey>> findAllJourney = () -> journeyRepository.findAll();

    public Function<Long, Journey> findById = (id) ->
            journeyRepository.findById(id)
                    .orElseThrow(() -> new NullPointerException("No journey is found with ID: " + id));


    public String generateRef(String prefix) {
        Random random = new Random();
        int suffixLength = 7;
        StringBuilder suffixBuilder = new StringBuilder();
        for (int i = 0; i < suffixLength; i++) {
            suffixBuilder.append(random.nextInt(10));
        }
        return prefix + suffixBuilder.toString();
    }
}
