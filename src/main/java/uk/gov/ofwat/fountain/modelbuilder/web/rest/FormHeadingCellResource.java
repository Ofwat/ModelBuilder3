package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;

import uk.gov.ofwat.fountain.modelbuilder.repository.FormHeadingCellRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormHeadingCellSearchRepository;
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
 * REST controller for managing FormHeadingCell.
 */
@RestController
@RequestMapping("/api")
public class FormHeadingCellResource {

    private final Logger log = LoggerFactory.getLogger(FormHeadingCellResource.class);

    private static final String ENTITY_NAME = "formHeadingCell";
        
    private final FormHeadingCellRepository formHeadingCellRepository;

    private final FormHeadingCellSearchRepository formHeadingCellSearchRepository;

    public FormHeadingCellResource(FormHeadingCellRepository formHeadingCellRepository, FormHeadingCellSearchRepository formHeadingCellSearchRepository) {
        this.formHeadingCellRepository = formHeadingCellRepository;
        this.formHeadingCellSearchRepository = formHeadingCellSearchRepository;
    }

    /**
     * POST  /form-heading-cells : Create a new formHeadingCell.
     *
     * @param formHeadingCell the formHeadingCell to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formHeadingCell, or with status 400 (Bad Request) if the formHeadingCell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-heading-cells")
    @Timed
    public ResponseEntity<FormHeadingCell> createFormHeadingCell(@Valid @RequestBody FormHeadingCell formHeadingCell) throws URISyntaxException {
        log.debug("REST request to save FormHeadingCell : {}", formHeadingCell);
        if (formHeadingCell.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formHeadingCell cannot already have an ID")).body(null);
        }
        FormHeadingCell result = formHeadingCellRepository.save(formHeadingCell);
        formHeadingCellSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/form-heading-cells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-heading-cells : Updates an existing formHeadingCell.
     *
     * @param formHeadingCell the formHeadingCell to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formHeadingCell,
     * or with status 400 (Bad Request) if the formHeadingCell is not valid,
     * or with status 500 (Internal Server Error) if the formHeadingCell couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-heading-cells")
    @Timed
    public ResponseEntity<FormHeadingCell> updateFormHeadingCell(@Valid @RequestBody FormHeadingCell formHeadingCell) throws URISyntaxException {
        log.debug("REST request to update FormHeadingCell : {}", formHeadingCell);
        if (formHeadingCell.getId() == null) {
            return createFormHeadingCell(formHeadingCell);
        }
        FormHeadingCell result = formHeadingCellRepository.save(formHeadingCell);
        formHeadingCellSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formHeadingCell.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-heading-cells : get all the formHeadingCells.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formHeadingCells in body
     */
    @GetMapping("/form-heading-cells")
    @Timed
    public List<FormHeadingCell> getAllFormHeadingCells() {
        log.debug("REST request to get all FormHeadingCells");
        List<FormHeadingCell> formHeadingCells = formHeadingCellRepository.findAll();
        return formHeadingCells;
    }

    /**
     * GET  /form-heading-cells/:id : get the "id" formHeadingCell.
     *
     * @param id the id of the formHeadingCell to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formHeadingCell, or with status 404 (Not Found)
     */
    @GetMapping("/form-heading-cells/{id}")
    @Timed
    public ResponseEntity<FormHeadingCell> getFormHeadingCell(@PathVariable Long id) {
        log.debug("REST request to get FormHeadingCell : {}", id);
        FormHeadingCell formHeadingCell = formHeadingCellRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formHeadingCell));
    }

    /**
     * DELETE  /form-heading-cells/:id : delete the "id" formHeadingCell.
     *
     * @param id the id of the formHeadingCell to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-heading-cells/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormHeadingCell(@PathVariable Long id) {
        log.debug("REST request to delete FormHeadingCell : {}", id);
        formHeadingCellRepository.delete(id);
        formHeadingCellSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-heading-cells?query=:query : search for the formHeadingCell corresponding
     * to the query.
     *
     * @param query the query of the formHeadingCell search 
     * @return the result of the search
     */
    @GetMapping("/_search/form-heading-cells")
    @Timed
    public List<FormHeadingCell> searchFormHeadingCells(@RequestParam String query) {
        log.debug("REST request to search FormHeadingCells for query {}", query);
        return StreamSupport
            .stream(formHeadingCellSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
