package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SectionDetails entity.
 */
@SuppressWarnings("unused")
public interface SectionDetailsRepository extends JpaRepository<SectionDetails,Long> {

}
