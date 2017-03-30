package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelBuilderDocument;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModelBuilderDocument entity.
 */
@SuppressWarnings("unused")
public interface ModelBuilderDocumentRepository extends JpaRepository<ModelBuilderDocument,Long> {

}
