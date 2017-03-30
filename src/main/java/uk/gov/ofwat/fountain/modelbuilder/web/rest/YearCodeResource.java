package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.YearCode;

import uk.gov.ofwat.fountain.modelbuilder.repository.YearCodeRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.YearCodeSearchRepository;
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
 * REST controller for managing YearCode.
 */
@RestController
@RequestMapping("/api")
public class YearCodeResource {

    private final Logger log = LoggerFactory.getLogger(YearCodeResource.class);

    private static final String ENTITY_NAME = "yearCode";
        
    private final YearCodeRepository yearCodeRepository;

    private final YearCodeSearchRepository yearCodeSearchRepository;

    public YearCodeResource(YearCodeRepository yearCodeRepository, YearCodeSearchRepository yearCodeSearchRepository) {
        this.yearCodeRepository = yearCodeRepository;
        this.yearCodeSearchRepository = yearCodeSearchRepository;
    }

    /**
     * POST  /year-codes : Create a new yearCode.
     *
     * @param yearCode the yearCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yearCode, or with status 400 (Bad Request) if the yearCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/year-codes")
    @Timed
    public ResponseEntity<YearCode> createYearCode(@Valid @RequestBody YearCode yearCode) throws URISyntaxException {
        log.debug("REST request to save YearCode : {}", yearCode);
        if (yearCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new yearCode cannot already have an ID")).body(null);
        }
        YearCode result = yearCodeRepository.save(yearCode);
        yearCodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/year-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /year-codes : Updates an existing yearCode.
     *
     * @param yearCode the yearCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yearCode,
     * or with status 400 (Bad Request) if the yearCode is not valid,
     * or with status 500 (Internal Server Error) if the yearCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/year-codes")
    @Timed
    public ResponseEntity<YearCode> updateYearCode(@Valid @RequestBody YearCode yearCode) throws URISyntaxException {
        log.debug("REST request to update YearCode : {}", yearCode);
        if (yearCode.getId() == null) {
            return createYearCode(yearCode);
        }
        YearCode result = yearCodeRepository.save(yearCode);
        yearCodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, yearCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /year-codes : get all the yearCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of yearCodes in body
     */
    @GetMapping("/year-codes")
    @Timed
    public List<YearCode> getAllYearCodes() {
        log.debug("REST request to get all YearCodes");
        List<YearCode> yearCodes = yearCodeRepository.findAll();
        return yearCodes;
    }

    /**
     * GET  /year-codes/:id : get the "id" yearCode.
     *
     * @param id the id of the yearCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yearCode, or with status 404 (Not Found)
     */
    @GetMapping("/year-codes/{id}")
    @Timed
    public ResponseEntity<YearCode> getYearCode(@PathVariable Long id) {
        log.debug("REST request to get YearCode : {}", id);
        YearCode yearCode = yearCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(yearCode));
    }

    /**
     * DELETE  /year-codes/:id : delete the "id" yearCode.
     *
     * @param id the id of the yearCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/year-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteYearCode(@PathVariable Long id) {
        log.debug("REST request to delete YearCode : {}", id);
        yearCodeRepository.delete(id);
        yearCodeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/year-codes?query=:query : search for the yearCode corresponding
     * to the query.
     *
     * @param query the query of the yearCode search 
     * @return the result of the search
     */
    @GetMapping("/_search/year-codes")
    @Timed
    public List<YearCode> searchYearCodes(@RequestParam String query) {
        log.debug("REST request to search YearCodes for query {}", query);
        return StreamSupport
            .stream(yearCodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
