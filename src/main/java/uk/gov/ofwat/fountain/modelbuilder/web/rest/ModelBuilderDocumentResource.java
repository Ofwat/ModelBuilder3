package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelBuilderDocument;

import uk.gov.ofwat.fountain.modelbuilder.repository.ModelBuilderDocumentRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ModelBuilderDocumentSearchRepository;
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
 * REST controller for managing ModelBuilderDocument.
 */
@RestController
@RequestMapping("/api")
public class ModelBuilderDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ModelBuilderDocumentResource.class);

    private static final String ENTITY_NAME = "modelBuilderDocument";
        
    private final ModelBuilderDocumentRepository modelBuilderDocumentRepository;

    private final ModelBuilderDocumentSearchRepository modelBuilderDocumentSearchRepository;

    public ModelBuilderDocumentResource(ModelBuilderDocumentRepository modelBuilderDocumentRepository, ModelBuilderDocumentSearchRepository modelBuilderDocumentSearchRepository) {
        this.modelBuilderDocumentRepository = modelBuilderDocumentRepository;
        this.modelBuilderDocumentSearchRepository = modelBuilderDocumentSearchRepository;
    }

    /**
     * POST  /model-builder-documents : Create a new modelBuilderDocument.
     *
     * @param modelBuilderDocument the modelBuilderDocument to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modelBuilderDocument, or with status 400 (Bad Request) if the modelBuilderDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/model-builder-documents")
    @Timed
    public ResponseEntity<ModelBuilderDocument> createModelBuilderDocument(@Valid @RequestBody ModelBuilderDocument modelBuilderDocument) throws URISyntaxException {
        log.debug("REST request to save ModelBuilderDocument : {}", modelBuilderDocument);
        if (modelBuilderDocument.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new modelBuilderDocument cannot already have an ID")).body(null);
        }
        ModelBuilderDocument result = modelBuilderDocumentRepository.save(modelBuilderDocument);
        modelBuilderDocumentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/model-builder-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /model-builder-documents : Updates an existing modelBuilderDocument.
     *
     * @param modelBuilderDocument the modelBuilderDocument to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modelBuilderDocument,
     * or with status 400 (Bad Request) if the modelBuilderDocument is not valid,
     * or with status 500 (Internal Server Error) if the modelBuilderDocument couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/model-builder-documents")
    @Timed
    public ResponseEntity<ModelBuilderDocument> updateModelBuilderDocument(@Valid @RequestBody ModelBuilderDocument modelBuilderDocument) throws URISyntaxException {
        log.debug("REST request to update ModelBuilderDocument : {}", modelBuilderDocument);
        if (modelBuilderDocument.getId() == null) {
            return createModelBuilderDocument(modelBuilderDocument);
        }
        ModelBuilderDocument result = modelBuilderDocumentRepository.save(modelBuilderDocument);
        modelBuilderDocumentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modelBuilderDocument.getId().toString()))
            .body(result);
    }

    /**
     * GET  /model-builder-documents : get all the modelBuilderDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modelBuilderDocuments in body
     */
    @GetMapping("/model-builder-documents")
    @Timed
    public List<ModelBuilderDocument> getAllModelBuilderDocuments() {
        log.debug("REST request to get all ModelBuilderDocuments");
        List<ModelBuilderDocument> modelBuilderDocuments = modelBuilderDocumentRepository.findAll();
        return modelBuilderDocuments;
    }

    /**
     * GET  /model-builder-documents/:id : get the "id" modelBuilderDocument.
     *
     * @param id the id of the modelBuilderDocument to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modelBuilderDocument, or with status 404 (Not Found)
     */
    @GetMapping("/model-builder-documents/{id}")
    @Timed
    public ResponseEntity<ModelBuilderDocument> getModelBuilderDocument(@PathVariable Long id) {
        log.debug("REST request to get ModelBuilderDocument : {}", id);
        ModelBuilderDocument modelBuilderDocument = modelBuilderDocumentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modelBuilderDocument));
    }

    /**
     * DELETE  /model-builder-documents/:id : delete the "id" modelBuilderDocument.
     *
     * @param id the id of the modelBuilderDocument to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/model-builder-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteModelBuilderDocument(@PathVariable Long id) {
        log.debug("REST request to delete ModelBuilderDocument : {}", id);
        modelBuilderDocumentRepository.delete(id);
        modelBuilderDocumentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/model-builder-documents?query=:query : search for the modelBuilderDocument corresponding
     * to the query.
     *
     * @param query the query of the modelBuilderDocument search 
     * @return the result of the search
     */
    @GetMapping("/_search/model-builder-documents")
    @Timed
    public List<ModelBuilderDocument> searchModelBuilderDocuments(@RequestParam String query) {
        log.debug("REST request to search ModelBuilderDocuments for query {}", query);
        return StreamSupport
            .stream(modelBuilderDocumentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
