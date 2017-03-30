package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuditDetails entity.
 */
@SuppressWarnings("unused")
public interface AuditDetailsRepository extends JpaRepository<AuditDetails,Long> {

}
