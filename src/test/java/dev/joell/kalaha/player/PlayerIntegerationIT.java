package dev.joell.kalaha.player;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerIntegerationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testListPlayerEmpty() throws Exception {
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    @Order(2)
    public void testCreatePlayer() throws Exception {
        mockMvc.perform(
                post("/api/players")
                        .contentType("application/json")
                        .content("{\"username\": \"joell\", \"password\": \"123qwe\"}"))
                .andExpect(status().isOk());

    }

    @Test
    @Order(3)
    public void testListPlayer() throws Exception {
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").value("joell"));
    }

    @Test
    @Order(3)
    public void testCreatePlayerRaisesOnDuplicate() throws Exception {
        mockMvc.perform(
                post("/api/players")
                        .contentType("application/json")
                        .content("{\"username\": \"joell\", \"password\": \"123qwe\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Player with name joell already exists"));

    }

    @Test
    public void testCreatePlayerWithEmptyBody() throws Exception {
        mockMvc.perform(
                post("/api/players")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePlayerWithEmptyName() throws Exception {
        mockMvc.perform(
                post("/api/players")
                        .contentType("application/json")
                        .content("{\"username\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("name is missing"));
    }

    @Test
    public void testCreatePlayerWithWeakPassword() throws Exception {
        mockMvc.perform(
                post("/api/players")
                        .contentType("application/json")
                        .content("{\"username\": \"jan\", \"password\": \"123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("password must be at least 6 characters"));
    }

}
