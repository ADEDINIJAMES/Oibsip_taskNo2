package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Long> {
    Optional<Journey> findJourneyById (Long id);

}
