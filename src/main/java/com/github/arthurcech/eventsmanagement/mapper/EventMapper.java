package com.github.arthurcech.eventsmanagement.mapper;

import com.github.arthurcech.eventsmanagement.domain.Event;
import com.github.arthurcech.eventsmanagement.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDTO toEventDTO(Event event);

    Event toEvent(EventDTO eventDTO);

}
