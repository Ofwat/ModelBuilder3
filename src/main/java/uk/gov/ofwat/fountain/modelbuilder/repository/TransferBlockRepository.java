package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransferBlock entity.
 */
@SuppressWarnings("unused")
public interface TransferBlockRepository extends JpaRepository<TransferBlock,Long> {

}
