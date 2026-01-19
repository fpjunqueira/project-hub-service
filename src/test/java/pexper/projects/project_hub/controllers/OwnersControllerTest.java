package pexper.projects.project_hub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.security.JwtService;
import pexper.projects.project_hub.services.OwnerService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnersController.class)
@AutoConfigureMockMvc(addFilters = false)
class OwnersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private OwnerService ownerService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void getAllReturnsPage() throws Exception {
        Owner owner = new Owner();
        owner.setId(2L);
        owner.setName("Ada");
        when(ownerService.findAll(PageRequest.of(0, 3)))
                .thenReturn(new PageImpl<>(List.of(owner)));

        mockMvc.perform(get("/api/owners?page=0&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(2L))
                .andExpect(jsonPath("$.content[0].name").value("Ada"));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(ownerService.findById(11L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/owners/11"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsCreated() throws Exception {
        Owner saved = new Owner();
        saved.setId(7L);
        saved.setName("Grace");
        when(ownerService.save(any(Owner.class))).thenReturn(saved);

        Owner payload = new Owner();
        payload.setName("Grace");

        mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").value("Grace"));
    }
}
