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
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.security.JwtService;
import pexper.projects.project_hub.services.ProjectService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectsController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void getAllReturnsPage() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setProjectName("Portal");
        when(projectService.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(List.of(project)));

        mockMvc.perform(get("/api/projects?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].projectName").value("Portal"));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(projectService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/projects/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsCreated() throws Exception {
        Project saved = new Project();
        saved.setId(3L);
        saved.setProjectName("Apollo");
        when(projectService.save(any(Project.class))).thenReturn(saved);

        Project payload = new Project();
        payload.setProjectName("Apollo");

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.projectName").value("Apollo"));
    }
}
