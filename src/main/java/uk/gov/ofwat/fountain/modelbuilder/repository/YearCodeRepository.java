package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.YearCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YearCode entity.
 */
@SuppressWarnings("unused")
public interface YearCodeRepository extends JpaRepository<YearCode,Long> {

}
