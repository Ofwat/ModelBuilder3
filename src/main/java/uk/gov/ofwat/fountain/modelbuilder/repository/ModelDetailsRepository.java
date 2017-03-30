package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModelDetails entity.
 */
@SuppressWarnings("unused")
public interface ModelDetailsRepository extends JpaRepository<ModelDetails,Long> {
    public ModelDetails findByName(String name);
}
