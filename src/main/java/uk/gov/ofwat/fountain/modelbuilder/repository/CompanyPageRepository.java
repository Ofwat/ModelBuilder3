package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyPage entity.
 */
@SuppressWarnings("unused")
public interface CompanyPageRepository extends JpaRepository<CompanyPage,Long> {

}
