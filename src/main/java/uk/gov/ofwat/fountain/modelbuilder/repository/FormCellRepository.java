package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FormCell entity.
 */
@SuppressWarnings("unused")
public interface FormCellRepository extends JpaRepository<FormCell,Long> {

}
