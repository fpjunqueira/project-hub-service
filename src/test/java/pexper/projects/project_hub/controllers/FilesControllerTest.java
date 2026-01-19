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
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.security.JwtService;
import pexper.projects.project_hub.services.FileService;
import pexper.projects.project_hub.services.ProjectService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilesController.class)
@AutoConfigureMockMvc(addFilters = false)
class FilesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FileService fileService;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void getAllReturnsPage() throws Exception {
        FileRecordDto dto = new FileRecordDto();
        dto.setId(6L);
        dto.setFilename("readme.txt");
        when(fileService.findAll(PageRequest.of(0, 4)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/files?page=0&size=4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(6L))
                .andExpect(jsonPath("$.content[0].filename").value("readme.txt"));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(fileService.findById(13L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/files/13"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsCreated() throws Exception {
        FileRecordDto saved = new FileRecordDto();
        saved.setId(8L);
        saved.setFilename("report.csv");
        when(fileService.save(any(FileRecordDto.class))).thenReturn(saved);

        FileRecordDto payload = new FileRecordDto();
        payload.setFilename("report.csv");

        mockMvc.perform(post("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(8L))
                .andExpect(jsonPath("$.filename").value("report.csv"));
    }
}
