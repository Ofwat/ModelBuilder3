package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.PageDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PageDetails entity.
 */
@SuppressWarnings("unused")
public interface PageDetailsRepository extends JpaRepository<PageDetails,Long> {

}
