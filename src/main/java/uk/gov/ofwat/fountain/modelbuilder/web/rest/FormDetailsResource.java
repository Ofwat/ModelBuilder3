package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.FormDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.FormDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormDetailsSearchRepository;
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
 * REST controller for managing FormDetails.
 */
@RestController
@RequestMapping("/api")
public class FormDetailsResource {

    private final Logger log = LoggerFactory.getLogger(FormDetailsResource.class);

    private static final String ENTITY_NAME = "formDetails";
        
    private final FormDetailsRepository formDetailsRepository;

    private final FormDetailsSearchRepository formDetailsSearchRepository;

    public FormDetailsResource(FormDetailsRepository formDetailsRepository, FormDetailsSearchRepository formDetailsSearchRepository) {
        this.formDetailsRepository = formDetailsRepository;
        this.formDetailsSearchRepository = formDetailsSearchRepository;
    }

    /**
     * POST  /form-details : Create a new formDetails.
     *
     * @param formDetails the formDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formDetails, or with status 400 (Bad Request) if the formDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-details")
    @Timed
    public ResponseEntity<FormDetails> createFormDetails(@RequestBody FormDetails formDetails) throws URISyntaxException {
        log.debug("REST request to save FormDetails : {}", formDetails);
        if (formDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formDetails cannot already have an ID")).body(null);
        }
        FormDetails result = formDetailsRepository.save(formDetails);
        formDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/form-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-details : Updates an existing formDetails.
     *
     * @param formDetails the formDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formDetails,
     * or with status 400 (Bad Request) if the formDetails is not valid,
     * or with status 500 (Internal Server Error) if the formDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-details")
    @Timed
    public ResponseEntity<FormDetails> updateFormDetails(@RequestBody FormDetails formDetails) throws URISyntaxException {
        log.debug("REST request to update FormDetails : {}", formDetails);
        if (formDetails.getId() == null) {
            return createFormDetails(formDetails);
        }
        FormDetails result = formDetailsRepository.save(formDetails);
        formDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-details : get all the formDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formDetails in body
     */
    @GetMapping("/form-details")
    @Timed
    public List<FormDetails> getAllFormDetails() {
        log.debug("REST request to get all FormDetails");
        List<FormDetails> formDetails = formDetailsRepository.findAll();
        return formDetails;
    }

    /**
     * GET  /form-details/:id : get the "id" formDetails.
     *
     * @param id the id of the formDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formDetails, or with status 404 (Not Found)
     */
    @GetMapping("/form-details/{id}")
    @Timed
    public ResponseEntity<FormDetails> getFormDetails(@PathVariable Long id) {
        log.debug("REST request to get FormDetails : {}", id);
        FormDetails formDetails = formDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formDetails));
    }

    /**
     * DELETE  /form-details/:id : delete the "id" formDetails.
     *
     * @param id the id of the formDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormDetails(@PathVariable Long id) {
        log.debug("REST request to delete FormDetails : {}", id);
        formDetailsRepository.delete(id);
        formDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-details?query=:query : search for the formDetails corresponding
     * to the query.
     *
     * @param query the query of the formDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/form-details")
    @Timed
    public List<FormDetails> searchFormDetails(@RequestParam String query) {
        log.debug("REST request to search FormDetails for query {}", query);
        return StreamSupport
            .stream(formDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
