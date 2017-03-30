package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.AuditDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.AuditDetailsSearchRepository;
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
 * REST controller for managing AuditDetails.
 */
@RestController
@RequestMapping("/api")
public class AuditDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AuditDetailsResource.class);

    private static final String ENTITY_NAME = "auditDetails";
        
    private final AuditDetailsRepository auditDetailsRepository;

    private final AuditDetailsSearchRepository auditDetailsSearchRepository;

    public AuditDetailsResource(AuditDetailsRepository auditDetailsRepository, AuditDetailsSearchRepository auditDetailsSearchRepository) {
        this.auditDetailsRepository = auditDetailsRepository;
        this.auditDetailsSearchRepository = auditDetailsSearchRepository;
    }

    /**
     * POST  /audit-details : Create a new auditDetails.
     *
     * @param auditDetails the auditDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auditDetails, or with status 400 (Bad Request) if the auditDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audit-details")
    @Timed
    public ResponseEntity<AuditDetails> createAuditDetails(@RequestBody AuditDetails auditDetails) throws URISyntaxException {
        log.debug("REST request to save AuditDetails : {}", auditDetails);
        if (auditDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auditDetails cannot already have an ID")).body(null);
        }
        AuditDetails result = auditDetailsRepository.save(auditDetails);
        auditDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/audit-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audit-details : Updates an existing auditDetails.
     *
     * @param auditDetails the auditDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auditDetails,
     * or with status 400 (Bad Request) if the auditDetails is not valid,
     * or with status 500 (Internal Server Error) if the auditDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audit-details")
    @Timed
    public ResponseEntity<AuditDetails> updateAuditDetails(@RequestBody AuditDetails auditDetails) throws URISyntaxException {
        log.debug("REST request to update AuditDetails : {}", auditDetails);
        if (auditDetails.getId() == null) {
            return createAuditDetails(auditDetails);
        }
        AuditDetails result = auditDetailsRepository.save(auditDetails);
        auditDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auditDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audit-details : get all the auditDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auditDetails in body
     */
    @GetMapping("/audit-details")
    @Timed
    public List<AuditDetails> getAllAuditDetails() {
        log.debug("REST request to get all AuditDetails");
        List<AuditDetails> auditDetails = auditDetailsRepository.findAll();
        return auditDetails;
    }

    /**
     * GET  /audit-details/:id : get the "id" auditDetails.
     *
     * @param id the id of the auditDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auditDetails, or with status 404 (Not Found)
     */
    @GetMapping("/audit-details/{id}")
    @Timed
    public ResponseEntity<AuditDetails> getAuditDetails(@PathVariable Long id) {
        log.debug("REST request to get AuditDetails : {}", id);
        AuditDetails auditDetails = auditDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auditDetails));
    }

    /**
     * DELETE  /audit-details/:id : delete the "id" auditDetails.
     *
     * @param id the id of the auditDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audit-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuditDetails(@PathVariable Long id) {
        log.debug("REST request to delete AuditDetails : {}", id);
        auditDetailsRepository.delete(id);
        auditDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/audit-details?query=:query : search for the auditDetails corresponding
     * to the query.
     *
     * @param query the query of the auditDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/audit-details")
    @Timed
    public List<AuditDetails> searchAuditDetails(@RequestParam String query) {
        log.debug("REST request to search AuditDetails for query {}", query);
        return StreamSupport
            .stream(auditDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
