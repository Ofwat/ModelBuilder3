package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Page;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Page entity.
 */
@SuppressWarnings("unused")
public interface PageRepository extends JpaRepository<Page,Long> {

}
