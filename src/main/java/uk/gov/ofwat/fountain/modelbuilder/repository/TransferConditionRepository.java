package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferCondition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransferCondition entity.
 */
@SuppressWarnings("unused")
public interface TransferConditionRepository extends JpaRepository<TransferCondition,Long> {

}
