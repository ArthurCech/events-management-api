package com.github.arthurcech.eventsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arthurcech.eventsmanagement.dto.EventDTO;
import com.github.arthurcech.eventsmanagement.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private String clientUsername;
    private String clientPassword;
    private String adminUsername;
    private String adminPassword;

    @BeforeEach
    void setUp() {
        clientUsername = "ana@gmail.com";
        clientPassword = "123456";
        adminUsername = "bob@gmail.com";
        adminPassword = "123456";
    }

    @Test
    void insertShouldReturn401WhenNoUserLogged() throws Exception {
        EventDTO dto = new EventDTO(null, "Expo XP", LocalDate.of(2021, 5, 18), "https://expoxp.com.br", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(post("/events")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void insertShouldInsertResourceWhenClientLoggedAndCorrectData() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Expo XP", nextMonth, "https://expoxp.com.br", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("Expo XP"));
        result.andExpect(jsonPath("$.date").value(nextMonth.toString()));
        result.andExpect(jsonPath("$.url").value("https://expoxp.com.br"));
        result.andExpect(jsonPath("$.cityId").value(1L));
    }

    @Test
    void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Expo XP", nextMonth, "https://expoxp.com.br", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("Expo XP"));
        result.andExpect(jsonPath("$.date").value(nextMonth.toString()));
        result.andExpect(jsonPath("$.url").value("https://expoxp.com.br"));
        result.andExpect(jsonPath("$.cityId").value(1L));
    }

    @Test
    void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "      ", nextMonth, "https://expoxp.com.br", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].field").value("name"));
        result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));
    }

    @Test
    void insertShouldReturn422WhenAdminLoggedAndPastDate() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        LocalDate pastMonth = LocalDate.now().minusMonths(1L);

        EventDTO dto = new EventDTO(null, "Expo XP", pastMonth, "https://expoxp.com.br", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].field").value("date"));
        result.andExpect(jsonPath("$.errors[0].message").value("A data do evento não pode ser passada"));
    }

    @Test
    void insertShouldReturn422WhenAdminLoggedAndNullCity() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Expo XP", nextMonth, "https://expoxp.com.br", null);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].field").value("cityId"));
        result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));
    }

    @Test
    void findAllShouldReturnPagedResources() throws Exception {
        ResultActions result = mockMvc.perform(get("/events")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
    }

}