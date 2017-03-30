package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.AuditChange;

import uk.gov.ofwat.fountain.modelbuilder.repository.AuditChangeRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.AuditChangeSearchRepository;
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
 * REST controller for managing AuditChange.
 */
@RestController
@RequestMapping("/api")
public class AuditChangeResource {

    private final Logger log = LoggerFactory.getLogger(AuditChangeResource.class);

    private static final String ENTITY_NAME = "auditChange";
        
    private final AuditChangeRepository auditChangeRepository;

    private final AuditChangeSearchRepository auditChangeSearchRepository;

    public AuditChangeResource(AuditChangeRepository auditChangeRepository, AuditChangeSearchRepository auditChangeSearchRepository) {
        this.auditChangeRepository = auditChangeRepository;
        this.auditChangeSearchRepository = auditChangeSearchRepository;
    }

    /**
     * POST  /audit-changes : Create a new auditChange.
     *
     * @param auditChange the auditChange to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auditChange, or with status 400 (Bad Request) if the auditChange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audit-changes")
    @Timed
    public ResponseEntity<AuditChange> createAuditChange(@RequestBody AuditChange auditChange) throws URISyntaxException {
        log.debug("REST request to save AuditChange : {}", auditChange);
        if (auditChange.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auditChange cannot already have an ID")).body(null);
        }
        AuditChange result = auditChangeRepository.save(auditChange);
        auditChangeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/audit-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audit-changes : Updates an existing auditChange.
     *
     * @param auditChange the auditChange to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auditChange,
     * or with status 400 (Bad Request) if the auditChange is not valid,
     * or with status 500 (Internal Server Error) if the auditChange couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audit-changes")
    @Timed
    public ResponseEntity<AuditChange> updateAuditChange(@RequestBody AuditChange auditChange) throws URISyntaxException {
        log.debug("REST request to update AuditChange : {}", auditChange);
        if (auditChange.getId() == null) {
            return createAuditChange(auditChange);
        }
        AuditChange result = auditChangeRepository.save(auditChange);
        auditChangeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auditChange.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audit-changes : get all the auditChanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auditChanges in body
     */
    @GetMapping("/audit-changes")
    @Timed
    public List<AuditChange> getAllAuditChanges() {
        log.debug("REST request to get all AuditChanges");
        List<AuditChange> auditChanges = auditChangeRepository.findAll();
        return auditChanges;
    }

    /**
     * GET  /audit-changes/:id : get the "id" auditChange.
     *
     * @param id the id of the auditChange to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auditChange, or with status 404 (Not Found)
     */
    @GetMapping("/audit-changes/{id}")
    @Timed
    public ResponseEntity<AuditChange> getAuditChange(@PathVariable Long id) {
        log.debug("REST request to get AuditChange : {}", id);
        AuditChange auditChange = auditChangeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auditChange));
    }

    /**
     * DELETE  /audit-changes/:id : delete the "id" auditChange.
     *
     * @param id the id of the auditChange to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audit-changes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuditChange(@PathVariable Long id) {
        log.debug("REST request to delete AuditChange : {}", id);
        auditChangeRepository.delete(id);
        auditChangeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/audit-changes?query=:query : search for the auditChange corresponding
     * to the query.
     *
     * @param query the query of the auditChange search 
     * @return the result of the search
     */
    @GetMapping("/_search/audit-changes")
    @Timed
    public List<AuditChange> searchAuditChanges(@RequestParam String query) {
        log.debug("REST request to search AuditChanges for query {}", query);
        return StreamSupport
            .stream(auditChangeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
