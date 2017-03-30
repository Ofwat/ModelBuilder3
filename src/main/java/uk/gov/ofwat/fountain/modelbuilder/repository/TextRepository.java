package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Text;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Text entity.
 */
@SuppressWarnings("unused")
public interface TextRepository extends JpaRepository<Text,Long> {

}
