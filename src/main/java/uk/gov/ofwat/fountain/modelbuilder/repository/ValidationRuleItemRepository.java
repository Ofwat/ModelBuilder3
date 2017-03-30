package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValidationRuleItem entity.
 */
@SuppressWarnings("unused")
public interface ValidationRuleItemRepository extends JpaRepository<ValidationRuleItem,Long> {

}
