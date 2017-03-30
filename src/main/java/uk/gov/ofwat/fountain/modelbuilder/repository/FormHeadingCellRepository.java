package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FormHeadingCell entity.
 */
@SuppressWarnings("unused")
public interface FormHeadingCellRepository extends JpaRepository<FormHeadingCell,Long> {

}
