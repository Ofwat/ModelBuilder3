package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.TextBlock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TextBlock entity.
 */
@SuppressWarnings("unused")
public interface TextBlockRepository extends JpaRepository<TextBlock,Long> {

}
