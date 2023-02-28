package com.github.arthurcech.eventsmanagement.mapper;

import com.github.arthurcech.eventsmanagement.domain.City;
import com.github.arthurcech.eventsmanagement.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO toCityDTO(City city);

    City toCity(CityDTO cityDTO);

}
