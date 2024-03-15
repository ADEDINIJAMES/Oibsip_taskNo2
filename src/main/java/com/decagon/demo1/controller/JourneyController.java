package com.decagon.demo1.controller;

import com.decagon.demo1.dto.JourneyDto;
import com.decagon.demo1.model.Journey;
import com.decagon.demo1.userService.JourneyServiceImplementation;
import com.decagon.demo1.userService.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/journey")
public class JourneyController {
    private JourneyServiceImplementation journeyServiceImplementation;
    private UserServiceImplementation userServiceImplementation; ;
    @Autowired
    public JourneyController(JourneyServiceImplementation journeyServiceImplementation, UserServiceImplementation userServiceImplementation){
        this.journeyServiceImplementation = journeyServiceImplementation;
        this.userServiceImplementation =userServiceImplementation;

    }
    @GetMapping("/all-journey")
    public ModelAndView findAllJourney(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Journey>jouneyList = journeyServiceImplementation.findAllJourney.get();
        return new ModelAndView("journey-dashboard")
                .addObject("journey", jouneyList)
                .addObject("cartItems", "Cart Items: "+ session.getAttribute("cartItems"));

    }

    @GetMapping("/admin-view")
public ModelAndView AdminViewProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Journey>journeyList = journeyServiceImplementation.findAllJourney.get();
        return new ModelAndView("admin-view-journey")
                .addObject("journey", journeyList);

    }

    @GetMapping("/admin-add-journey")
    public ModelAndView AdminAddProductsPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        return new ModelAndView("admin-add-journey")
                .addObject("journey", new JourneyDto());
    }


    @PostMapping("/admin-add-prod")
    public String  AdminAddProduct(@ModelAttribute JourneyDto journeyDto){
        Journey journey = journeyServiceImplementation.saveProduct.apply(journeyDto);
        System.out.println("user details----> {}"+ journey);
        return "redirect:/journey/admin-view";
    }

    @GetMapping("/delete-journey")
    public String DeleteProduct(@RequestParam Long id, HttpServletRequest request, Model model){
        request.getSession();
        journeyServiceImplementation.deleteJourney(id, request);
        return "redirect:/journey/admin-view";
    }
    @GetMapping("/edit-journey")
    public ModelAndView EditProduct(@RequestParam Long id, HttpSession session, HttpServletRequest request){
        session.getAttribute("userID");
        request.getSession();
       Journey journey= journeyServiceImplementation.findById.apply(id);
        return new ModelAndView("admin-add-journey")
                .addObject("journey",journey);
    }




}
