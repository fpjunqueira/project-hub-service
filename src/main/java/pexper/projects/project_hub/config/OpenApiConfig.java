package pexper.projects.project_hub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Project Hub API",
                version = "v1",
                description = "API documentation for Project Hub services."
        )
)
public class OpenApiConfig {
}
