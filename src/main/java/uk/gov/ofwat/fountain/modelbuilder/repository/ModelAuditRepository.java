package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModelAudit entity.
 */
@SuppressWarnings("unused")
public interface ModelAuditRepository extends JpaRepository<ModelAudit,Long> {

}
