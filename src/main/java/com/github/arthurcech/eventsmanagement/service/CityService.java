package com.github.arthurcech.eventsmanagement.service;

import com.github.arthurcech.eventsmanagement.domain.City;
import com.github.arthurcech.eventsmanagement.dto.CityDTO;
import com.github.arthurcech.eventsmanagement.mapper.CityMapper;
import com.github.arthurcech.eventsmanagement.repository.CityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> cities = cityRepository.findAll(Sort.by("name"));
        return cities.stream()
                .map(CityMapper.INSTANCE::toCityDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO cityDTO) {
        City city = CityMapper.INSTANCE.toCity(cityDTO);
        cityRepository.save(city);
        return CityMapper.INSTANCE.toCityDTO(city);
    }

}
