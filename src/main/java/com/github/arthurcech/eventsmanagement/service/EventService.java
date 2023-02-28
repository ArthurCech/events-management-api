package com.github.arthurcech.eventsmanagement.service;

import com.github.arthurcech.eventsmanagement.domain.Event;
import com.github.arthurcech.eventsmanagement.dto.EventDTO;
import com.github.arthurcech.eventsmanagement.mapper.EventMapper;
import com.github.arthurcech.eventsmanagement.repository.CityRepository;
import com.github.arthurcech.eventsmanagement.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CityRepository cityRepository;

    public EventService(
            EventRepository eventRepository,
            CityRepository cityRepository
    ) {
        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(event -> {
            EventDTO eventDTO = EventMapper.INSTANCE.toEventDTO(event);
            eventDTO.setCityId(event.getCity().getId());
            return eventDTO;
        });
    }

    @Transactional
    public EventDTO insert(EventDTO dto) {
        Event event = EventMapper.INSTANCE.toEvent(dto);
        event.setCity(cityRepository.getOne(dto.getCityId()));
        eventRepository.save(event);
        EventDTO eventDTO = EventMapper.INSTANCE.toEventDTO(event);
        eventDTO.setCityId(event.getCity().getId());
        return eventDTO;
    }

}
