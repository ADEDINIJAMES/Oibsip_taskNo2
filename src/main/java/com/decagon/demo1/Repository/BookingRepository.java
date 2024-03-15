package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Booking;
import com.decagon.demo1.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser (Users users);
}
