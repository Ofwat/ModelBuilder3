package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlock;

import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferBlockSearchRepository;
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
 * REST controller for managing TransferBlock.
 */
@RestController
@RequestMapping("/api")
public class TransferBlockResource {

    private final Logger log = LoggerFactory.getLogger(TransferBlockResource.class);

    private static final String ENTITY_NAME = "transferBlock";
        
    private final TransferBlockRepository transferBlockRepository;

    private final TransferBlockSearchRepository transferBlockSearchRepository;

    public TransferBlockResource(TransferBlockRepository transferBlockRepository, TransferBlockSearchRepository transferBlockSearchRepository) {
        this.transferBlockRepository = transferBlockRepository;
        this.transferBlockSearchRepository = transferBlockSearchRepository;
    }

    /**
     * POST  /transfer-blocks : Create a new transferBlock.
     *
     * @param transferBlock the transferBlock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transferBlock, or with status 400 (Bad Request) if the transferBlock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transfer-blocks")
    @Timed
    public ResponseEntity<TransferBlock> createTransferBlock(@RequestBody TransferBlock transferBlock) throws URISyntaxException {
        log.debug("REST request to save TransferBlock : {}", transferBlock);
        if (transferBlock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transferBlock cannot already have an ID")).body(null);
        }
        TransferBlock result = transferBlockRepository.save(transferBlock);
        transferBlockSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transfer-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transfer-blocks : Updates an existing transferBlock.
     *
     * @param transferBlock the transferBlock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transferBlock,
     * or with status 400 (Bad Request) if the transferBlock is not valid,
     * or with status 500 (Internal Server Error) if the transferBlock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transfer-blocks")
    @Timed
    public ResponseEntity<TransferBlock> updateTransferBlock(@RequestBody TransferBlock transferBlock) throws URISyntaxException {
        log.debug("REST request to update TransferBlock : {}", transferBlock);
        if (transferBlock.getId() == null) {
            return createTransferBlock(transferBlock);
        }
        TransferBlock result = transferBlockRepository.save(transferBlock);
        transferBlockSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transferBlock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transfer-blocks : get all the transferBlocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transferBlocks in body
     */
    @GetMapping("/transfer-blocks")
    @Timed
    public List<TransferBlock> getAllTransferBlocks() {
        log.debug("REST request to get all TransferBlocks");
        List<TransferBlock> transferBlocks = transferBlockRepository.findAll();
        return transferBlocks;
    }

    /**
     * GET  /transfer-blocks/:id : get the "id" transferBlock.
     *
     * @param id the id of the transferBlock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transferBlock, or with status 404 (Not Found)
     */
    @GetMapping("/transfer-blocks/{id}")
    @Timed
    public ResponseEntity<TransferBlock> getTransferBlock(@PathVariable Long id) {
        log.debug("REST request to get TransferBlock : {}", id);
        TransferBlock transferBlock = transferBlockRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transferBlock));
    }

    /**
     * DELETE  /transfer-blocks/:id : delete the "id" transferBlock.
     *
     * @param id the id of the transferBlock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transfer-blocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransferBlock(@PathVariable Long id) {
        log.debug("REST request to delete TransferBlock : {}", id);
        transferBlockRepository.delete(id);
        transferBlockSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transfer-blocks?query=:query : search for the transferBlock corresponding
     * to the query.
     *
     * @param query the query of the transferBlock search 
     * @return the result of the search
     */
    @GetMapping("/_search/transfer-blocks")
    @Timed
    public List<TransferBlock> searchTransferBlocks(@RequestParam String query) {
        log.debug("REST request to search TransferBlocks for query {}", query);
        return StreamSupport
            .stream(transferBlockSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
