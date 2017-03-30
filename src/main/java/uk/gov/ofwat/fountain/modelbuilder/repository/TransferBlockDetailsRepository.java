package uk.gov.ofwat.fountain.modelbuilder.repository;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransferBlockDetails entity.
 */
@SuppressWarnings("unused")
public interface TransferBlockDetailsRepository extends JpaRepository<TransferBlockDetails,Long> {

}
