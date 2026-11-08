package com.university.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Integration Test 1: GET /api/students with Admin Basic Auth returns 200 OK")
    void testGetAllStudents_WithAdminAuth_Returns200() throws Exception {
        mockMvc.perform(get("/api/students")
                        .with(httpBasic("admin", "adpass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Integration Test 2: POST /api/students with Admin Basic Auth creates student and returns 201 Created")
    void testCreateStudent_WithAdminAuth_Returns201() throws Exception {
        String newStudentJson = """
                {
                    "roll": "2207099",
                    "name": "Integration Test Student",
                    "email": "integration.test@gmail.com",
                    "department": "Computer Science"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .with(httpBasic("admin", "adpass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newStudentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roll").value("2207099"));
    }

    @Test
    @DisplayName("Integration Test 3: GET /api/students without authentication returns 401 Unauthorized")
    void testGetAllStudents_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isUnauthorized());
    }
}

