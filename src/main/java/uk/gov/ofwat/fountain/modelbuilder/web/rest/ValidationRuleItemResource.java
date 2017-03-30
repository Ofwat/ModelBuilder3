package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleItem;

import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleItemRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ValidationRuleItemSearchRepository;
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
 * REST controller for managing ValidationRuleItem.
 */
@RestController
@RequestMapping("/api")
public class ValidationRuleItemResource {

    private final Logger log = LoggerFactory.getLogger(ValidationRuleItemResource.class);

    private static final String ENTITY_NAME = "validationRuleItem";
        
    private final ValidationRuleItemRepository validationRuleItemRepository;

    private final ValidationRuleItemSearchRepository validationRuleItemSearchRepository;

    public ValidationRuleItemResource(ValidationRuleItemRepository validationRuleItemRepository, ValidationRuleItemSearchRepository validationRuleItemSearchRepository) {
        this.validationRuleItemRepository = validationRuleItemRepository;
        this.validationRuleItemSearchRepository = validationRuleItemSearchRepository;
    }

    /**
     * POST  /validation-rule-items : Create a new validationRuleItem.
     *
     * @param validationRuleItem the validationRuleItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new validationRuleItem, or with status 400 (Bad Request) if the validationRuleItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/validation-rule-items")
    @Timed
    public ResponseEntity<ValidationRuleItem> createValidationRuleItem(@Valid @RequestBody ValidationRuleItem validationRuleItem) throws URISyntaxException {
        log.debug("REST request to save ValidationRuleItem : {}", validationRuleItem);
        if (validationRuleItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new validationRuleItem cannot already have an ID")).body(null);
        }
        ValidationRuleItem result = validationRuleItemRepository.save(validationRuleItem);
        validationRuleItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/validation-rule-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /validation-rule-items : Updates an existing validationRuleItem.
     *
     * @param validationRuleItem the validationRuleItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated validationRuleItem,
     * or with status 400 (Bad Request) if the validationRuleItem is not valid,
     * or with status 500 (Internal Server Error) if the validationRuleItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/validation-rule-items")
    @Timed
    public ResponseEntity<ValidationRuleItem> updateValidationRuleItem(@Valid @RequestBody ValidationRuleItem validationRuleItem) throws URISyntaxException {
        log.debug("REST request to update ValidationRuleItem : {}", validationRuleItem);
        if (validationRuleItem.getId() == null) {
            return createValidationRuleItem(validationRuleItem);
        }
        ValidationRuleItem result = validationRuleItemRepository.save(validationRuleItem);
        validationRuleItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, validationRuleItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /validation-rule-items : get all the validationRuleItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of validationRuleItems in body
     */
    @GetMapping("/validation-rule-items")
    @Timed
    public List<ValidationRuleItem> getAllValidationRuleItems() {
        log.debug("REST request to get all ValidationRuleItems");
        List<ValidationRuleItem> validationRuleItems = validationRuleItemRepository.findAll();
        return validationRuleItems;
    }

    /**
     * GET  /validation-rule-items/:id : get the "id" validationRuleItem.
     *
     * @param id the id of the validationRuleItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the validationRuleItem, or with status 404 (Not Found)
     */
    @GetMapping("/validation-rule-items/{id}")
    @Timed
    public ResponseEntity<ValidationRuleItem> getValidationRuleItem(@PathVariable Long id) {
        log.debug("REST request to get ValidationRuleItem : {}", id);
        ValidationRuleItem validationRuleItem = validationRuleItemRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(validationRuleItem));
    }

    /**
     * DELETE  /validation-rule-items/:id : delete the "id" validationRuleItem.
     *
     * @param id the id of the validationRuleItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/validation-rule-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteValidationRuleItem(@PathVariable Long id) {
        log.debug("REST request to delete ValidationRuleItem : {}", id);
        validationRuleItemRepository.delete(id);
        validationRuleItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/validation-rule-items?query=:query : search for the validationRuleItem corresponding
     * to the query.
     *
     * @param query the query of the validationRuleItem search 
     * @return the result of the search
     */
    @GetMapping("/_search/validation-rule-items")
    @Timed
    public List<ValidationRuleItem> searchValidationRuleItems(@RequestParam String query) {
        log.debug("REST request to search ValidationRuleItems for query {}", query);
        return StreamSupport
            .stream(validationRuleItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
