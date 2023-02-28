package com.github.arthurcech.eventsmanagement.dto;

import javax.validation.constraints.NotBlank;

public class CityDTO {

    private Long id;
    @NotBlank(message = "Campo requerido")
    private String name;

    public CityDTO(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
