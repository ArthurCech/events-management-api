package com.github.arthurcech.eventsmanagement.controller;

import com.github.arthurcech.eventsmanagement.dto.CityDTO;
import com.github.arthurcech.eventsmanagement.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityDTO> findAll() {
        return cityService.findAll();
    }

    @PostMapping
    public ResponseEntity<CityDTO> insert(
            @RequestBody @Valid CityDTO payload
    ) {
        CityDTO response = cityService.insert(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
