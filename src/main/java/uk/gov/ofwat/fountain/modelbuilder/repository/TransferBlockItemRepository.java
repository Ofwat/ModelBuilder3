package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransferBlockItem entity.
 */
@SuppressWarnings("unused")
public interface TransferBlockItemRepository extends JpaRepository<TransferBlockItem,Long> {

}
