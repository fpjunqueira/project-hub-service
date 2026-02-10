package pexper.projects.project_hub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.ClaroSite;
import pexper.projects.project_hub.domain.TimSite;
import pexper.projects.project_hub.domain.VivoSite;
import pexper.projects.project_hub.repositories.ClaroSiteRepository;
import pexper.projects.project_hub.repositories.TimSiteRepository;
import pexper.projects.project_hub.repositories.VivoSiteRepository;

@RestController
@RequestMapping("/api/site-lookup")
public class SiteLookupController {

    private final ClaroSiteRepository claroSiteRepository;
    private final TimSiteRepository timSiteRepository;
    private final VivoSiteRepository vivoSiteRepository;

    public SiteLookupController(ClaroSiteRepository claroSiteRepository,
                                TimSiteRepository timSiteRepository,
                                VivoSiteRepository vivoSiteRepository) {
        this.claroSiteRepository = claroSiteRepository;
        this.timSiteRepository = timSiteRepository;
        this.vivoSiteRepository = vivoSiteRepository;
    }

    @GetMapping
    public Object lookup(@RequestParam String siteType,
                         @RequestParam(required = false) String siteId,
                         @RequestParam(required = false) String addressId) {
        if ((siteId == null || siteId.isBlank()) && (addressId == null || addressId.isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "siteId or addressId is required");
        }

        return switch (siteType.toLowerCase()) {
            case "claro" -> findClaroSite(siteId, addressId);
            case "tim" -> findTimSite(siteId, addressId);
            case "vivo" -> findVivoSite(siteId, addressId);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported siteType: " + siteType);
        };
    }

    private ClaroSite findClaroSite(String siteId, String addressId) {
        if (siteId != null && !siteId.isBlank()) {
            return claroSiteRepository.findBySiteId(siteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claro site not found: " + siteId));
        }
        return claroSiteRepository.findByAddressId(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claro site not found for address: " + addressId));
    }

    private TimSite findTimSite(String siteId, String addressId) {
        if (siteId != null && !siteId.isBlank()) {
            return timSiteRepository.findBySiteId(siteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TIM site not found: " + siteId));
        }
        return timSiteRepository.findByAddressId(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TIM site not found for address: " + addressId));
    }

    private VivoSite findVivoSite(String siteId, String addressId) {
        if (siteId != null && !siteId.isBlank()) {
            return vivoSiteRepository.findBySequence(siteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vivo site not found: " + siteId));
        }
        return vivoSiteRepository.findByAddressId(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vivo site not found for address: " + addressId));
    }
}
