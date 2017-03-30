package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ValidationRuleDetailsSearchRepository;
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
 * REST controller for managing ValidationRuleDetails.
 */
@RestController
@RequestMapping("/api")
public class ValidationRuleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ValidationRuleDetailsResource.class);

    private static final String ENTITY_NAME = "validationRuleDetails";
        
    private final ValidationRuleDetailsRepository validationRuleDetailsRepository;

    private final ValidationRuleDetailsSearchRepository validationRuleDetailsSearchRepository;

    public ValidationRuleDetailsResource(ValidationRuleDetailsRepository validationRuleDetailsRepository, ValidationRuleDetailsSearchRepository validationRuleDetailsSearchRepository) {
        this.validationRuleDetailsRepository = validationRuleDetailsRepository;
        this.validationRuleDetailsSearchRepository = validationRuleDetailsSearchRepository;
    }

    /**
     * POST  /validation-rule-details : Create a new validationRuleDetails.
     *
     * @param validationRuleDetails the validationRuleDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new validationRuleDetails, or with status 400 (Bad Request) if the validationRuleDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/validation-rule-details")
    @Timed
    public ResponseEntity<ValidationRuleDetails> createValidationRuleDetails(@Valid @RequestBody ValidationRuleDetails validationRuleDetails) throws URISyntaxException {
        log.debug("REST request to save ValidationRuleDetails : {}", validationRuleDetails);
        if (validationRuleDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new validationRuleDetails cannot already have an ID")).body(null);
        }
        ValidationRuleDetails result = validationRuleDetailsRepository.save(validationRuleDetails);
        validationRuleDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/validation-rule-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /validation-rule-details : Updates an existing validationRuleDetails.
     *
     * @param validationRuleDetails the validationRuleDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated validationRuleDetails,
     * or with status 400 (Bad Request) if the validationRuleDetails is not valid,
     * or with status 500 (Internal Server Error) if the validationRuleDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/validation-rule-details")
    @Timed
    public ResponseEntity<ValidationRuleDetails> updateValidationRuleDetails(@Valid @RequestBody ValidationRuleDetails validationRuleDetails) throws URISyntaxException {
        log.debug("REST request to update ValidationRuleDetails : {}", validationRuleDetails);
        if (validationRuleDetails.getId() == null) {
            return createValidationRuleDetails(validationRuleDetails);
        }
        ValidationRuleDetails result = validationRuleDetailsRepository.save(validationRuleDetails);
        validationRuleDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, validationRuleDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /validation-rule-details : get all the validationRuleDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of validationRuleDetails in body
     */
    @GetMapping("/validation-rule-details")
    @Timed
    public List<ValidationRuleDetails> getAllValidationRuleDetails() {
        log.debug("REST request to get all ValidationRuleDetails");
        List<ValidationRuleDetails> validationRuleDetails = validationRuleDetailsRepository.findAll();
        return validationRuleDetails;
    }

    /**
     * GET  /validation-rule-details/:id : get the "id" validationRuleDetails.
     *
     * @param id the id of the validationRuleDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the validationRuleDetails, or with status 404 (Not Found)
     */
    @GetMapping("/validation-rule-details/{id}")
    @Timed
    public ResponseEntity<ValidationRuleDetails> getValidationRuleDetails(@PathVariable Long id) {
        log.debug("REST request to get ValidationRuleDetails : {}", id);
        ValidationRuleDetails validationRuleDetails = validationRuleDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(validationRuleDetails));
    }

    /**
     * DELETE  /validation-rule-details/:id : delete the "id" validationRuleDetails.
     *
     * @param id the id of the validationRuleDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/validation-rule-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteValidationRuleDetails(@PathVariable Long id) {
        log.debug("REST request to delete ValidationRuleDetails : {}", id);
        validationRuleDetailsRepository.delete(id);
        validationRuleDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/validation-rule-details?query=:query : search for the validationRuleDetails corresponding
     * to the query.
     *
     * @param query the query of the validationRuleDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/validation-rule-details")
    @Timed
    public List<ValidationRuleDetails> searchValidationRuleDetails(@RequestParam String query) {
        log.debug("REST request to search ValidationRuleDetails for query {}", query);
        return StreamSupport
            .stream(validationRuleDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
