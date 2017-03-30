package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValidationRuleDetails entity.
 */
@SuppressWarnings("unused")
public interface ValidationRuleDetailsRepository extends JpaRepository<ValidationRuleDetails,Long> {

}
