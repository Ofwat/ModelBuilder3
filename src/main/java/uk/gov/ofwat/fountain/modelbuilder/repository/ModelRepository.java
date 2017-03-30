package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Model;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Model entity.
 */
@SuppressWarnings("unused")
public interface ModelRepository extends JpaRepository<Model,Long> {

}
