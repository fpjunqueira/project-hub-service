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
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.security.JwtService;
import pexper.projects.project_hub.services.AddressService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressesController.class)
@AutoConfigureMockMvc(addFilters = false)
class AddressesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AddressService addressService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void getAllReturnsPage() throws Exception {
        Address address = new Address();
        address.setId(1L);
        when(addressService.findAll(PageRequest.of(0, 2)))
                .thenReturn(new PageImpl<>(List.of(address)));

        mockMvc.perform(get("/api/addresses?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(addressService.findById(9L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/addresses/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsCreated() throws Exception {
        Address saved = new Address();
        saved.setId(5L);
        when(addressService.save(any(Address.class))).thenReturn(saved);

        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Address())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5L));
    }
}
