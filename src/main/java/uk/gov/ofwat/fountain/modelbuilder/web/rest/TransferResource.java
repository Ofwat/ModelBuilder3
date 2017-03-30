package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Transfer;

import uk.gov.ofwat.fountain.modelbuilder.repository.TransferRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferSearchRepository;
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
 * REST controller for managing Transfer.
 */
@RestController
@RequestMapping("/api")
public class TransferResource {

    private final Logger log = LoggerFactory.getLogger(TransferResource.class);

    private static final String ENTITY_NAME = "transfer";
        
    private final TransferRepository transferRepository;

    private final TransferSearchRepository transferSearchRepository;

    public TransferResource(TransferRepository transferRepository, TransferSearchRepository transferSearchRepository) {
        this.transferRepository = transferRepository;
        this.transferSearchRepository = transferSearchRepository;
    }

    /**
     * POST  /transfers : Create a new transfer.
     *
     * @param transfer the transfer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transfer, or with status 400 (Bad Request) if the transfer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transfers")
    @Timed
    public ResponseEntity<Transfer> createTransfer(@RequestBody Transfer transfer) throws URISyntaxException {
        log.debug("REST request to save Transfer : {}", transfer);
        if (transfer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transfer cannot already have an ID")).body(null);
        }
        Transfer result = transferRepository.save(transfer);
        transferSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transfers : Updates an existing transfer.
     *
     * @param transfer the transfer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transfer,
     * or with status 400 (Bad Request) if the transfer is not valid,
     * or with status 500 (Internal Server Error) if the transfer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transfers")
    @Timed
    public ResponseEntity<Transfer> updateTransfer(@RequestBody Transfer transfer) throws URISyntaxException {
        log.debug("REST request to update Transfer : {}", transfer);
        if (transfer.getId() == null) {
            return createTransfer(transfer);
        }
        Transfer result = transferRepository.save(transfer);
        transferSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transfer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transfers : get all the transfers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transfers in body
     */
    @GetMapping("/transfers")
    @Timed
    public List<Transfer> getAllTransfers() {
        log.debug("REST request to get all Transfers");
        List<Transfer> transfers = transferRepository.findAll();
        return transfers;
    }

    /**
     * GET  /transfers/:id : get the "id" transfer.
     *
     * @param id the id of the transfer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transfer, or with status 404 (Not Found)
     */
    @GetMapping("/transfers/{id}")
    @Timed
    public ResponseEntity<Transfer> getTransfer(@PathVariable Long id) {
        log.debug("REST request to get Transfer : {}", id);
        Transfer transfer = transferRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transfer));
    }

    /**
     * DELETE  /transfers/:id : delete the "id" transfer.
     *
     * @param id the id of the transfer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transfers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransfer(@PathVariable Long id) {
        log.debug("REST request to delete Transfer : {}", id);
        transferRepository.delete(id);
        transferSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transfers?query=:query : search for the transfer corresponding
     * to the query.
     *
     * @param query the query of the transfer search 
     * @return the result of the search
     */
    @GetMapping("/_search/transfers")
    @Timed
    public List<Transfer> searchTransfers(@RequestParam String query) {
        log.debug("REST request to search Transfers for query {}", query);
        return StreamSupport
            .stream(transferSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
