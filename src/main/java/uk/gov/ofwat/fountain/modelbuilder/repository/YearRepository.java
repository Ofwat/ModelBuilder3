package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Year;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Year entity.
 */
@SuppressWarnings("unused")
public interface YearRepository extends JpaRepository<Year,Long> {

}
