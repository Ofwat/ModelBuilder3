package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange;

import uk.gov.ofwat.fountain.modelbuilder.repository.CellRangeRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.CellRangeSearchRepository;
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
 * REST controller for managing CellRange.
 */
@RestController
@RequestMapping("/api")
public class CellRangeResource {

    private final Logger log = LoggerFactory.getLogger(CellRangeResource.class);

    private static final String ENTITY_NAME = "cellRange";
        
    private final CellRangeRepository cellRangeRepository;

    private final CellRangeSearchRepository cellRangeSearchRepository;

    public CellRangeResource(CellRangeRepository cellRangeRepository, CellRangeSearchRepository cellRangeSearchRepository) {
        this.cellRangeRepository = cellRangeRepository;
        this.cellRangeSearchRepository = cellRangeSearchRepository;
    }

    /**
     * POST  /cell-ranges : Create a new cellRange.
     *
     * @param cellRange the cellRange to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cellRange, or with status 400 (Bad Request) if the cellRange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cell-ranges")
    @Timed
    public ResponseEntity<CellRange> createCellRange(@Valid @RequestBody CellRange cellRange) throws URISyntaxException {
        log.debug("REST request to save CellRange : {}", cellRange);
        if (cellRange.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cellRange cannot already have an ID")).body(null);
        }
        CellRange result = cellRangeRepository.save(cellRange);
        cellRangeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cell-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cell-ranges : Updates an existing cellRange.
     *
     * @param cellRange the cellRange to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cellRange,
     * or with status 400 (Bad Request) if the cellRange is not valid,
     * or with status 500 (Internal Server Error) if the cellRange couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cell-ranges")
    @Timed
    public ResponseEntity<CellRange> updateCellRange(@Valid @RequestBody CellRange cellRange) throws URISyntaxException {
        log.debug("REST request to update CellRange : {}", cellRange);
        if (cellRange.getId() == null) {
            return createCellRange(cellRange);
        }
        CellRange result = cellRangeRepository.save(cellRange);
        cellRangeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cellRange.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cell-ranges : get all the cellRanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cellRanges in body
     */
    @GetMapping("/cell-ranges")
    @Timed
    public List<CellRange> getAllCellRanges() {
        log.debug("REST request to get all CellRanges");
        List<CellRange> cellRanges = cellRangeRepository.findAll();
        return cellRanges;
    }

    /**
     * GET  /cell-ranges/:id : get the "id" cellRange.
     *
     * @param id the id of the cellRange to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cellRange, or with status 404 (Not Found)
     */
    @GetMapping("/cell-ranges/{id}")
    @Timed
    public ResponseEntity<CellRange> getCellRange(@PathVariable Long id) {
        log.debug("REST request to get CellRange : {}", id);
        CellRange cellRange = cellRangeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cellRange));
    }

    /**
     * DELETE  /cell-ranges/:id : delete the "id" cellRange.
     *
     * @param id the id of the cellRange to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cell-ranges/{id}")
    @Timed
    public ResponseEntity<Void> deleteCellRange(@PathVariable Long id) {
        log.debug("REST request to delete CellRange : {}", id);
        cellRangeRepository.delete(id);
        cellRangeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cell-ranges?query=:query : search for the cellRange corresponding
     * to the query.
     *
     * @param query the query of the cellRange search 
     * @return the result of the search
     */
    @GetMapping("/_search/cell-ranges")
    @Timed
    public List<CellRange> searchCellRanges(@RequestParam String query) {
        log.debug("REST request to search CellRanges for query {}", query);
        return StreamSupport
            .stream(cellRangeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
