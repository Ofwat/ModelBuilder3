package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.Transfer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Transfer entity.
 */
@SuppressWarnings("unused")
public interface TransferRepository extends JpaRepository<Transfer,Long> {

}
