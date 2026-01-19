package pexper.projects.project_hub.services;

import org.springframework.core.io.Resource;

public record FileDownload(Resource resource, String filename, String contentType) {
}
