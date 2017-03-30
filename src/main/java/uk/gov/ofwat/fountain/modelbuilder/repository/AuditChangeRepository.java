package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditChange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuditChange entity.
 */
@SuppressWarnings("unused")
public interface AuditChangeRepository extends JpaRepository<AuditChange,Long> {

}
