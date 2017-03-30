package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FormDetails entity.
 */
@SuppressWarnings("unused")
public interface FormDetailsRepository extends JpaRepository<FormDetails,Long> {

}
