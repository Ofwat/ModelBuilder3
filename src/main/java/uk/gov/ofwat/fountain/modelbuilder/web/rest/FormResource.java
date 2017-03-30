package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Form;

import uk.gov.ofwat.fountain.modelbuilder.repository.FormRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormSearchRepository;
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
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";
        
    private final FormRepository formRepository;

    private final FormSearchRepository formSearchRepository;

    public FormResource(FormRepository formRepository, FormSearchRepository formSearchRepository) {
        this.formRepository = formRepository;
        this.formSearchRepository = formSearchRepository;
    }

    /**
     * POST  /forms : Create a new form.
     *
     * @param form the form to create
     * @return the ResponseEntity with status 201 (Created) and with body the new form, or with status 400 (Bad Request) if the form has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forms")
    @Timed
    public ResponseEntity<Form> createForm(@RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to save Form : {}", form);
        if (form.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new form cannot already have an ID")).body(null);
        }
        Form result = formRepository.save(form);
        formSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms : Updates an existing form.
     *
     * @param form the form to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated form,
     * or with status 400 (Bad Request) if the form is not valid,
     * or with status 500 (Internal Server Error) if the form couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forms")
    @Timed
    public ResponseEntity<Form> updateForm(@RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to update Form : {}", form);
        if (form.getId() == null) {
            return createForm(form);
        }
        Form result = formRepository.save(form);
        formSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, form.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms : get all the forms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forms in body
     */
    @GetMapping("/forms")
    @Timed
    public List<Form> getAllForms() {
        log.debug("REST request to get all Forms");
        List<Form> forms = formRepository.findAll();
        return forms;
    }

    /**
     * GET  /forms/:id : get the "id" form.
     *
     * @param id the id of the form to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the form, or with status 404 (Not Found)
     */
    @GetMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Form> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Form form = formRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(form));
    }

    /**
     * DELETE  /forms/:id : delete the "id" form.
     *
     * @param id the id of the form to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formRepository.delete(id);
        formSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/forms?query=:query : search for the form corresponding
     * to the query.
     *
     * @param query the query of the form search 
     * @return the result of the search
     */
    @GetMapping("/_search/forms")
    @Timed
    public List<Form> searchForms(@RequestParam String query) {
        log.debug("REST request to search Forms for query {}", query);
        return StreamSupport
            .stream(formSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
