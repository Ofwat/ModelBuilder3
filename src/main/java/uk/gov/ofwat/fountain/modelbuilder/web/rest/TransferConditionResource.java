package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferCondition;

import uk.gov.ofwat.fountain.modelbuilder.repository.TransferConditionRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferConditionSearchRepository;
import uk.gov.ofwat.fountain.modelbuilder.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TransferCondition.
 */
@RestController
@RequestMapping("/api")
public class TransferConditionResource {

    private final Logger log = LoggerFactory.getLogger(TransferConditionResource.class);

    private static final String ENTITY_NAME = "transferCondition";
        
    private final TransferConditionRepository transferConditionRepository;

    private final TransferConditionSearchRepository transferConditionSearchRepository;

    public TransferConditionResource(TransferConditionRepository transferConditionRepository, TransferConditionSearchRepository transferConditionSearchRepository) {
        this.transferConditionRepository = transferConditionRepository;
        this.transferConditionSearchRepository = transferConditionSearchRepository;
    }

    /**
     * POST  /transfer-conditions : Create a new transferCondition.
     *
     * @param transferCondition the transferCondition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transferCondition, or with status 400 (Bad Request) if the transferCondition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transfer-conditions")
    @Timed
    public ResponseEntity<TransferCondition> createTransferCondition(@Valid @RequestBody TransferCondition transferCondition) throws URISyntaxException {
        log.debug("REST request to save TransferCondition : {}", transferCondition);
        if (transferCondition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transferCondition cannot already have an ID")).body(null);
        }
        TransferCondition result = transferConditionRepository.save(transferCondition);
        transferConditionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transfer-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transfer-conditions : Updates an existing transferCondition.
     *
     * @param transferCondition the transferCondition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transferCondition,
     * or with status 400 (Bad Request) if the transferCondition is not valid,
     * or with status 500 (Internal Server Error) if the transferCondition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transfer-conditions")
    @Timed
    public ResponseEntity<TransferCondition> updateTransferCondition(@Valid @RequestBody TransferCondition transferCondition) throws URISyntaxException {
        log.debug("REST request to update TransferCondition : {}", transferCondition);
        if (transferCondition.getId() == null) {
            return createTransferCondition(transferCondition);
        }
        TransferCondition result = transferConditionRepository.save(transferCondition);
        transferConditionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transferCondition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transfer-conditions : get all the transferConditions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transferConditions in body
     */
    @GetMapping("/transfer-conditions")
    @Timed
    public List<TransferCondition> getAllTransferConditions() {
        log.debug("REST request to get all TransferConditions");
        List<TransferCondition> transferConditions = transferConditionRepository.findAll();
        return transferConditions;
    }

    /**
     * GET  /transfer-conditions/:id : get the "id" transferCondition.
     *
     * @param id the id of the transferCondition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transferCondition, or with status 404 (Not Found)
     */
    @GetMapping("/transfer-conditions/{id}")
    @Timed
    public ResponseEntity<TransferCondition> getTransferCondition(@PathVariable Long id) {
        log.debug("REST request to get TransferCondition : {}", id);
        TransferCondition transferCondition = transferConditionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transferCondition));
    }

    /**
     * DELETE  /transfer-conditions/:id : delete the "id" transferCondition.
     *
     * @param id the id of the transferCondition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transfer-conditions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransferCondition(@PathVariable Long id) {
        log.debug("REST request to delete TransferCondition : {}", id);
        transferConditionRepository.delete(id);
        transferConditionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transfer-conditions?query=:query : search for the transferCondition corresponding
     * to the query.
     *
     * @param query the query of the transferCondition search 
     * @return the result of the search
     */
    @GetMapping("/_search/transfer-conditions")
    @Timed
    public List<TransferCondition> searchTransferConditions(@RequestParam String query) {
        log.debug("REST request to search TransferConditions for query {}", query);
        return StreamSupport
            .stream(transferConditionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
