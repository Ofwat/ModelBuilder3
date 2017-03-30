package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferBlockDetailsSearchRepository;
import uk.gov.ofwat.fountain.modelbuilder.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TransferBlockDetails.
 */
@RestController
@RequestMapping("/api")
public class TransferBlockDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TransferBlockDetailsResource.class);

    private static final String ENTITY_NAME = "transferBlockDetails";
        
    private final TransferBlockDetailsRepository transferBlockDetailsRepository;

    private final TransferBlockDetailsSearchRepository transferBlockDetailsSearchRepository;

    public TransferBlockDetailsResource(TransferBlockDetailsRepository transferBlockDetailsRepository, TransferBlockDetailsSearchRepository transferBlockDetailsSearchRepository) {
        this.transferBlockDetailsRepository = transferBlockDetailsRepository;
        this.transferBlockDetailsSearchRepository = transferBlockDetailsSearchRepository;
    }

    /**
     * POST  /transfer-block-details : Create a new transferBlockDetails.
     *
     * @param transferBlockDetails the transferBlockDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transferBlockDetails, or with status 400 (Bad Request) if the transferBlockDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transfer-block-details")
    @Timed
    public ResponseEntity<TransferBlockDetails> createTransferBlockDetails(@RequestBody TransferBlockDetails transferBlockDetails) throws URISyntaxException {
        log.debug("REST request to save TransferBlockDetails : {}", transferBlockDetails);
        if (transferBlockDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transferBlockDetails cannot already have an ID")).body(null);
        }
        TransferBlockDetails result = transferBlockDetailsRepository.save(transferBlockDetails);
        transferBlockDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transfer-block-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transfer-block-details : Updates an existing transferBlockDetails.
     *
     * @param transferBlockDetails the transferBlockDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transferBlockDetails,
     * or with status 400 (Bad Request) if the transferBlockDetails is not valid,
     * or with status 500 (Internal Server Error) if the transferBlockDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transfer-block-details")
    @Timed
    public ResponseEntity<TransferBlockDetails> updateTransferBlockDetails(@RequestBody TransferBlockDetails transferBlockDetails) throws URISyntaxException {
        log.debug("REST request to update TransferBlockDetails : {}", transferBlockDetails);
        if (transferBlockDetails.getId() == null) {
            return createTransferBlockDetails(transferBlockDetails);
        }
        TransferBlockDetails result = transferBlockDetailsRepository.save(transferBlockDetails);
        transferBlockDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transferBlockDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transfer-block-details : get all the transferBlockDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transferBlockDetails in body
     */
    @GetMapping("/transfer-block-details")
    @Timed
    public List<TransferBlockDetails> getAllTransferBlockDetails() {
        log.debug("REST request to get all TransferBlockDetails");
        List<TransferBlockDetails> transferBlockDetails = transferBlockDetailsRepository.findAll();
        return transferBlockDetails;
    }

    /**
     * GET  /transfer-block-details/:id : get the "id" transferBlockDetails.
     *
     * @param id the id of the transferBlockDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transferBlockDetails, or with status 404 (Not Found)
     */
    @GetMapping("/transfer-block-details/{id}")
    @Timed
    public ResponseEntity<TransferBlockDetails> getTransferBlockDetails(@PathVariable Long id) {
        log.debug("REST request to get TransferBlockDetails : {}", id);
        TransferBlockDetails transferBlockDetails = transferBlockDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transferBlockDetails));
    }

    /**
     * DELETE  /transfer-block-details/:id : delete the "id" transferBlockDetails.
     *
     * @param id the id of the transferBlockDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transfer-block-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransferBlockDetails(@PathVariable Long id) {
        log.debug("REST request to delete TransferBlockDetails : {}", id);
        transferBlockDetailsRepository.delete(id);
        transferBlockDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transfer-block-details?query=:query : search for the transferBlockDetails corresponding
     * to the query.
     *
     * @param query the query of the transferBlockDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/transfer-block-details")
    @Timed
    public List<TransferBlockDetails> searchTransferBlockDetails(@RequestParam String query) {
        log.debug("REST request to search TransferBlockDetails for query {}", query);
        return StreamSupport
            .stream(transferBlockDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
