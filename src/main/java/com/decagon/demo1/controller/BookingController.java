package com.decagon.demo1.controller;

import com.decagon.demo1.dto.BookingDto;
import com.decagon.demo1.dto.JourneyDto;
import com.decagon.demo1.model.Booking;
import com.decagon.demo1.model.Journey;
import com.decagon.demo1.model.Users;
import com.decagon.demo1.userService.BookingServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private  final BookingServiceImplementation bookingServiceImplementation ;

@Autowired
    public BookingController(BookingServiceImplementation bookingServiceImplementation) {
        this.bookingServiceImplementation = bookingServiceImplementation;
    }

    @GetMapping("/bookings")
    public ModelAndView findAllBooking(HttpServletRequest request, HttpSession session){
session.getAttribute("userID");
        List<BookingDto> BookingList = bookingServiceImplementation.findByUser.apply((Long) (session.getAttribute("userID")));
        return new ModelAndView("booking-dashboard")
                .addObject("bookings", BookingList);
    }
    @GetMapping("/passenger-book")
    public String  bookTrain (HttpSession session, @RequestParam Long id, HttpServletRequest request) throws Exception {
        session.getAttribute("userID");
        Booking booking = bookingServiceImplementation.bookJourney((Long) session.getAttribute("userID"), id, request);
        session.getAttribute("userID");
        System.out.println("user details----> {}"+ booking);
        return "redirect:/booking/bookings";
    }

    @GetMapping("/cancel-book")
    public String  cancelBook ( HttpServletRequest request,HttpSession session, @RequestParam Long id) throws Exception {
        session.getAttribute("userID");
    bookingServiceImplementation.cancelBooking(id,request);
        System.out.println("Booking details----> {}"+ "deleted");
        return "redirect:/booking/bookings";
    }


}