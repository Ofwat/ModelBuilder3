package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRule;

import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ValidationRuleSearchRepository;
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
 * REST controller for managing ValidationRule.
 */
@RestController
@RequestMapping("/api")
public class ValidationRuleResource {

    private final Logger log = LoggerFactory.getLogger(ValidationRuleResource.class);

    private static final String ENTITY_NAME = "validationRule";
        
    private final ValidationRuleRepository validationRuleRepository;

    private final ValidationRuleSearchRepository validationRuleSearchRepository;

    public ValidationRuleResource(ValidationRuleRepository validationRuleRepository, ValidationRuleSearchRepository validationRuleSearchRepository) {
        this.validationRuleRepository = validationRuleRepository;
        this.validationRuleSearchRepository = validationRuleSearchRepository;
    }

    /**
     * POST  /validation-rules : Create a new validationRule.
     *
     * @param validationRule the validationRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new validationRule, or with status 400 (Bad Request) if the validationRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/validation-rules")
    @Timed
    public ResponseEntity<ValidationRule> createValidationRule(@RequestBody ValidationRule validationRule) throws URISyntaxException {
        log.debug("REST request to save ValidationRule : {}", validationRule);
        if (validationRule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new validationRule cannot already have an ID")).body(null);
        }
        ValidationRule result = validationRuleRepository.save(validationRule);
        validationRuleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/validation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /validation-rules : Updates an existing validationRule.
     *
     * @param validationRule the validationRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated validationRule,
     * or with status 400 (Bad Request) if the validationRule is not valid,
     * or with status 500 (Internal Server Error) if the validationRule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/validation-rules")
    @Timed
    public ResponseEntity<ValidationRule> updateValidationRule(@RequestBody ValidationRule validationRule) throws URISyntaxException {
        log.debug("REST request to update ValidationRule : {}", validationRule);
        if (validationRule.getId() == null) {
            return createValidationRule(validationRule);
        }
        ValidationRule result = validationRuleRepository.save(validationRule);
        validationRuleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, validationRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /validation-rules : get all the validationRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of validationRules in body
     */
    @GetMapping("/validation-rules")
    @Timed
    public List<ValidationRule> getAllValidationRules() {
        log.debug("REST request to get all ValidationRules");
        List<ValidationRule> validationRules = validationRuleRepository.findAll();
        return validationRules;
    }

    /**
     * GET  /validation-rules/:id : get the "id" validationRule.
     *
     * @param id the id of the validationRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the validationRule, or with status 404 (Not Found)
     */
    @GetMapping("/validation-rules/{id}")
    @Timed
    public ResponseEntity<ValidationRule> getValidationRule(@PathVariable Long id) {
        log.debug("REST request to get ValidationRule : {}", id);
        ValidationRule validationRule = validationRuleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(validationRule));
    }

    /**
     * DELETE  /validation-rules/:id : delete the "id" validationRule.
     *
     * @param id the id of the validationRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/validation-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteValidationRule(@PathVariable Long id) {
        log.debug("REST request to delete ValidationRule : {}", id);
        validationRuleRepository.delete(id);
        validationRuleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/validation-rules?query=:query : search for the validationRule corresponding
     * to the query.
     *
     * @param query the query of the validationRule search 
     * @return the result of the search
     */
    @GetMapping("/_search/validation-rules")
    @Timed
    public List<ValidationRule> searchValidationRules(@RequestParam String query) {
        log.debug("REST request to search ValidationRules for query {}", query);
        return StreamSupport
            .stream(validationRuleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
