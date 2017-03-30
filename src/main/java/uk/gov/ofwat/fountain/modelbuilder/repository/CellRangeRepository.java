package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CellRange entity.
 */
@SuppressWarnings("unused")
public interface CellRangeRepository extends JpaRepository<CellRange,Long> {

}
