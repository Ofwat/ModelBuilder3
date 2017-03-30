package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValidationRule entity.
 */
@SuppressWarnings("unused")
public interface ValidationRuleRepository extends JpaRepository<ValidationRule,Long> {

}
