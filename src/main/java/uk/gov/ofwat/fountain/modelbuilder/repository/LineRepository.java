package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Line;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Line entity.
 */
@SuppressWarnings("unused")
public interface LineRepository extends JpaRepository<Line,Long> {

}
