package com.github.arthurcech.eventsmanagement.repository;

import com.github.arthurcech.eventsmanagement.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
