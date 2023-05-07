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
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import dev.joell.kalaha.player.dtos.PlayerDto;

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
                .content("{\"name\": \"joell\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("joell"));

        // TODO: not sure how to do proper test for this due the generated timestamp
    }

    @Test
    @Order(3)
    public void testListPlayer() throws Exception {
        mockMvc.perform(get("/api/players"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].name").value("joell"));
    }

    @Test
    @Order(3)
    public void testCreatePlayerRaisesOnDuplicate() throws Exception {
        mockMvc.perform(
            post("/api/players")
                .contentType("application/json")
                .content("{\"name\": \"joell\"}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Player with name joell already exists"));

    }

    @Test
    public void testCreatePlayerWithEmptyBody() throws Exception {
        mockMvc.perform(
                post("/api/players")
                    .contentType("application/json")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePlayerWithEmptyName() throws Exception {
        mockMvc.perform(
                post("/api/players")
                    .contentType("application/json")
                    .content("{\"name\": \"\"}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name").value( "name is missing"));
    }

}

