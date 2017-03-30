package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Heading;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Heading entity.
 */
@SuppressWarnings("unused")
public interface HeadingRepository extends JpaRepository<Heading,Long> {

}
