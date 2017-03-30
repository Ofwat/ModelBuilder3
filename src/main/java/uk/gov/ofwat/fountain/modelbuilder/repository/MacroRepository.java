package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Macro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Macro entity.
 */
@SuppressWarnings("unused")
public interface MacroRepository extends JpaRepository<Macro,Long> {

}
