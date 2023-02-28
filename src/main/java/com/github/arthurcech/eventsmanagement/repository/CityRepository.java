package com.github.arthurcech.eventsmanagement.repository;

import com.github.arthurcech.eventsmanagement.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
