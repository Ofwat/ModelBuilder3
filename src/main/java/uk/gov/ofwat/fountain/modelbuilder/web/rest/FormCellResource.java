package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell;

import uk.gov.ofwat.fountain.modelbuilder.repository.FormCellRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormCellSearchRepository;
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
 * REST controller for managing FormCell.
 */
@RestController
@RequestMapping("/api")
public class FormCellResource {

    private final Logger log = LoggerFactory.getLogger(FormCellResource.class);

    private static final String ENTITY_NAME = "formCell";
        
    private final FormCellRepository formCellRepository;

    private final FormCellSearchRepository formCellSearchRepository;

    public FormCellResource(FormCellRepository formCellRepository, FormCellSearchRepository formCellSearchRepository) {
        this.formCellRepository = formCellRepository;
        this.formCellSearchRepository = formCellSearchRepository;
    }

    /**
     * POST  /form-cells : Create a new formCell.
     *
     * @param formCell the formCell to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formCell, or with status 400 (Bad Request) if the formCell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-cells")
    @Timed
    public ResponseEntity<FormCell> createFormCell(@Valid @RequestBody FormCell formCell) throws URISyntaxException {
        log.debug("REST request to save FormCell : {}", formCell);
        if (formCell.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formCell cannot already have an ID")).body(null);
        }
        FormCell result = formCellRepository.save(formCell);
        formCellSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/form-cells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-cells : Updates an existing formCell.
     *
     * @param formCell the formCell to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formCell,
     * or with status 400 (Bad Request) if the formCell is not valid,
     * or with status 500 (Internal Server Error) if the formCell couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-cells")
    @Timed
    public ResponseEntity<FormCell> updateFormCell(@Valid @RequestBody FormCell formCell) throws URISyntaxException {
        log.debug("REST request to update FormCell : {}", formCell);
        if (formCell.getId() == null) {
            return createFormCell(formCell);
        }
        FormCell result = formCellRepository.save(formCell);
        formCellSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formCell.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-cells : get all the formCells.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formCells in body
     */
    @GetMapping("/form-cells")
    @Timed
    public List<FormCell> getAllFormCells() {
        log.debug("REST request to get all FormCells");
        List<FormCell> formCells = formCellRepository.findAll();
        return formCells;
    }

    /**
     * GET  /form-cells/:id : get the "id" formCell.
     *
     * @param id the id of the formCell to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formCell, or with status 404 (Not Found)
     */
    @GetMapping("/form-cells/{id}")
    @Timed
    public ResponseEntity<FormCell> getFormCell(@PathVariable Long id) {
        log.debug("REST request to get FormCell : {}", id);
        FormCell formCell = formCellRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formCell));
    }

    /**
     * DELETE  /form-cells/:id : delete the "id" formCell.
     *
     * @param id the id of the formCell to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-cells/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormCell(@PathVariable Long id) {
        log.debug("REST request to delete FormCell : {}", id);
        formCellRepository.delete(id);
        formCellSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-cells?query=:query : search for the formCell corresponding
     * to the query.
     *
     * @param query the query of the formCell search 
     * @return the result of the search
     */
    @GetMapping("/_search/form-cells")
    @Timed
    public List<FormCell> searchFormCells(@RequestParam String query) {
        log.debug("REST request to search FormCells for query {}", query);
        return StreamSupport
            .stream(formCellSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
