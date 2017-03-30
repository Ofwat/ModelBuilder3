package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit;

import uk.gov.ofwat.fountain.modelbuilder.repository.ModelAuditRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ModelAuditSearchRepository;
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
 * REST controller for managing ModelAudit.
 */
@RestController
@RequestMapping("/api")
public class ModelAuditResource {

    private final Logger log = LoggerFactory.getLogger(ModelAuditResource.class);

    private static final String ENTITY_NAME = "modelAudit";
        
    private final ModelAuditRepository modelAuditRepository;

    private final ModelAuditSearchRepository modelAuditSearchRepository;

    public ModelAuditResource(ModelAuditRepository modelAuditRepository, ModelAuditSearchRepository modelAuditSearchRepository) {
        this.modelAuditRepository = modelAuditRepository;
        this.modelAuditSearchRepository = modelAuditSearchRepository;
    }

    /**
     * POST  /model-audits : Create a new modelAudit.
     *
     * @param modelAudit the modelAudit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modelAudit, or with status 400 (Bad Request) if the modelAudit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/model-audits")
    @Timed
    public ResponseEntity<ModelAudit> createModelAudit(@RequestBody ModelAudit modelAudit) throws URISyntaxException {
        log.debug("REST request to save ModelAudit : {}", modelAudit);
        if (modelAudit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new modelAudit cannot already have an ID")).body(null);
        }
        ModelAudit result = modelAuditRepository.save(modelAudit);
        modelAuditSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/model-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /model-audits : Updates an existing modelAudit.
     *
     * @param modelAudit the modelAudit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modelAudit,
     * or with status 400 (Bad Request) if the modelAudit is not valid,
     * or with status 500 (Internal Server Error) if the modelAudit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/model-audits")
    @Timed
    public ResponseEntity<ModelAudit> updateModelAudit(@RequestBody ModelAudit modelAudit) throws URISyntaxException {
        log.debug("REST request to update ModelAudit : {}", modelAudit);
        if (modelAudit.getId() == null) {
            return createModelAudit(modelAudit);
        }
        ModelAudit result = modelAuditRepository.save(modelAudit);
        modelAuditSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modelAudit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /model-audits : get all the modelAudits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modelAudits in body
     */
    @GetMapping("/model-audits")
    @Timed
    public List<ModelAudit> getAllModelAudits() {
        log.debug("REST request to get all ModelAudits");
        List<ModelAudit> modelAudits = modelAuditRepository.findAll();
        return modelAudits;
    }

    /**
     * GET  /model-audits/:id : get the "id" modelAudit.
     *
     * @param id the id of the modelAudit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modelAudit, or with status 404 (Not Found)
     */
    @GetMapping("/model-audits/{id}")
    @Timed
    public ResponseEntity<ModelAudit> getModelAudit(@PathVariable Long id) {
        log.debug("REST request to get ModelAudit : {}", id);
        ModelAudit modelAudit = modelAuditRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modelAudit));
    }

    /**
     * DELETE  /model-audits/:id : delete the "id" modelAudit.
     *
     * @param id the id of the modelAudit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/model-audits/{id}")
    @Timed
    public ResponseEntity<Void> deleteModelAudit(@PathVariable Long id) {
        log.debug("REST request to delete ModelAudit : {}", id);
        modelAuditRepository.delete(id);
        modelAuditSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/model-audits?query=:query : search for the modelAudit corresponding
     * to the query.
     *
     * @param query the query of the modelAudit search 
     * @return the result of the search
     */
    @GetMapping("/_search/model-audits")
    @Timed
    public List<ModelAudit> searchModelAudits(@RequestParam String query) {
        log.debug("REST request to search ModelAudits for query {}", query);
        return StreamSupport
            .stream(modelAuditSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
