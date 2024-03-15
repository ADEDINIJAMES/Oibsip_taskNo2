package com.decagon.demo1.userService;

import com.decagon.demo1.Repository.BookingRepository;
import com.decagon.demo1.Repository.JourneyRepository;
import com.decagon.demo1.Repository.PassengerRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.dto.BookingDto;
import com.decagon.demo1.enums.Classes;
import com.decagon.demo1.model.Booking;
import com.decagon.demo1.model.Journey;
import com.decagon.demo1.model.Passenger;
import com.decagon.demo1.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class BookingServiceImplementation {

    private UserRepository userRepository ;
    private BookingRepository bookingRepository ;
    private JourneyRepository journeyRepository;
    private  PassengerRepository passengerRepository;

    @Autowired
    public BookingServiceImplementation(UserRepository userRepository, BookingRepository bookingRepository, JourneyRepository journeyRepository, PassengerRepository passengerRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.journeyRepository = journeyRepository;
        this.passengerRepository = passengerRepository;
    }

    public Function<Long, BookingDto> findBookingById =(id)-> {
        try {
            Booking booking=  bookingRepository.findById(id).orElseThrow(()-> new Exception("booking not Found"));
            BookingDto bookingDto = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(booking, BookingDto.class);
            return  bookingDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public Supplier<List<BookingDto>> findAllBooking = ()->{

        List<Booking> bookings =bookingRepository.findAll();
        List<BookingDto>bookingDtoList = new ArrayList<>();
        for(Booking booking : bookings){
            BookingDto bookingDto = new BookingDto(booking);
        bookingDtoList.add(bookingDto);
        }
    return bookingDtoList;
    };



    public Function<Long, List<BookingDto>> findByUser = (id)-> {
        Users users = null;
        try {
            users = userRepository.findById(id).orElseThrow(()-> new UserPrincipalNotFoundException("user not found"));
        } catch (UserPrincipalNotFoundException e) {
            return null;
        }
       List<Booking> bookingList = bookingRepository.findAllByUser(users);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for(Booking booking: bookingList){
            BookingDto bookingDto = new BookingDto(booking);
        bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    };


    public Booking bookJourney ( Long userId, Long id, HttpServletRequest request ) throws Exception {
        HttpSession session = request.getSession();
        Journey journey = journeyRepository.findJourneyById(id).orElseThrow(()-> new Exception("Not Found"));
        Users users = userRepository.findById(userId).orElseThrow(()-> new Exception ("user Not Found"));
        if (users.getBalance().compareTo( journey.getFare())> 0) {
            users.setBalance(users.getBalance().subtract(journey.getFare()));
            Users savedUsersDetail = userRepository.save(users);
            Booking booking = new Booking();
            booking.setJourneyId(journey);
            Passenger passenger = new Passenger();
            passenger.setPNR(generatePNR("PNR"));
            passenger.setAddress(users.getAddress());
            passenger.setFullName(users.getFullName());
            passenger.setPhone(users.getPhone());
            passenger.setTitle(users.getTitle());
            passenger.setEmail(users.getUsername());
            Passenger savedPassenger = passengerRepository.save(passenger);
            booking.setUser(users);
            booking.setUserBalance(savedUsersDetail.getBalance());
            booking.setClasses(Classes.ECONOMY);
            booking.setPassenger(savedPassenger);
            return bookingRepository.save(booking);
        }
throw new Exception("balance not enough");
    }


    public void cancelBooking(Long id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
      Booking booking =  bookingRepository.findById(id).orElseThrow(()-> new Exception ("not found"));
      booking.setUserBalance(booking.getUser().getBalance().add (booking.getJourneyId().getFare()));
      bookingRepository.delete(booking);

    }
    public String generatePNR(String prefix) {
        Random random = new Random();
        int suffixLength = 7;
        StringBuilder suffixBuilder = new StringBuilder();
        for (int i = 0; i < suffixLength; i++) {
            suffixBuilder.append(random.nextInt(10));
        }
        return prefix + suffixBuilder.toString();
    }


}
