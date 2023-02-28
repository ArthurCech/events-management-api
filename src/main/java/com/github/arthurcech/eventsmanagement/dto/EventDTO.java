package com.github.arthurcech.eventsmanagement.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EventDTO {

    private Long id;
    @NotBlank(message = "Campo requerido")
    private String name;
    @FutureOrPresent(message = "A data do evento não pode ser passada")
    private LocalDate date;
    private String url;
    @NotNull(message = "Campo requerido")
    private Long cityId;

    public EventDTO(
            Long id,
            String name,
            LocalDate date,
            String url,
            Long cityId
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.url = url;
        this.cityId = cityId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

}
