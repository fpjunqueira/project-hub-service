package pexper.projects.project_hub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimSite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String siteId;
    private String elementType;
    private String technology;
    private String connectionType;
    private String classification;
    private String acquisitionDate;
    private String constructionDate;
    private String activationDate;
    private String deactivationDate;
    private String justification;
    private String towerType;
    private String nominalAev;
    private String structureHeight;
    private String spazioUpdateStatus;
}
