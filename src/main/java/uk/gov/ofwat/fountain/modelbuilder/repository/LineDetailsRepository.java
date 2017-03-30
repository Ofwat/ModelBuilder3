package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LineDetails entity.
 */
@SuppressWarnings("unused")
public interface LineDetailsRepository extends JpaRepository<LineDetails,Long> {

}
