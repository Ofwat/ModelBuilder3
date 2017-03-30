package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Input;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Input entity.
 */
@SuppressWarnings("unused")
public interface InputRepository extends JpaRepository<Input,Long> {

}
