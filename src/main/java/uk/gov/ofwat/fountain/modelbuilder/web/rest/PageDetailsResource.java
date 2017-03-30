package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.PageDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.PageDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.PageDetailsSearchRepository;
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
 * REST controller for managing PageDetails.
 */
@RestController
@RequestMapping("/api")
public class PageDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PageDetailsResource.class);

    private static final String ENTITY_NAME = "pageDetails";
        
    private final PageDetailsRepository pageDetailsRepository;

    private final PageDetailsSearchRepository pageDetailsSearchRepository;

    public PageDetailsResource(PageDetailsRepository pageDetailsRepository, PageDetailsSearchRepository pageDetailsSearchRepository) {
        this.pageDetailsRepository = pageDetailsRepository;
        this.pageDetailsSearchRepository = pageDetailsSearchRepository;
    }

    /**
     * POST  /page-details : Create a new pageDetails.
     *
     * @param pageDetails the pageDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pageDetails, or with status 400 (Bad Request) if the pageDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/page-details")
    @Timed
    public ResponseEntity<PageDetails> createPageDetails(@Valid @RequestBody PageDetails pageDetails) throws URISyntaxException {
        log.debug("REST request to save PageDetails : {}", pageDetails);
        if (pageDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pageDetails cannot already have an ID")).body(null);
        }
        PageDetails result = pageDetailsRepository.save(pageDetails);
        pageDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/page-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /page-details : Updates an existing pageDetails.
     *
     * @param pageDetails the pageDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pageDetails,
     * or with status 400 (Bad Request) if the pageDetails is not valid,
     * or with status 500 (Internal Server Error) if the pageDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/page-details")
    @Timed
    public ResponseEntity<PageDetails> updatePageDetails(@Valid @RequestBody PageDetails pageDetails) throws URISyntaxException {
        log.debug("REST request to update PageDetails : {}", pageDetails);
        if (pageDetails.getId() == null) {
            return createPageDetails(pageDetails);
        }
        PageDetails result = pageDetailsRepository.save(pageDetails);
        pageDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pageDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /page-details : get all the pageDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pageDetails in body
     */
    @GetMapping("/page-details")
    @Timed
    public List<PageDetails> getAllPageDetails() {
        log.debug("REST request to get all PageDetails");
        List<PageDetails> pageDetails = pageDetailsRepository.findAll();
        return pageDetails;
    }

    /**
     * GET  /page-details/:id : get the "id" pageDetails.
     *
     * @param id the id of the pageDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pageDetails, or with status 404 (Not Found)
     */
    @GetMapping("/page-details/{id}")
    @Timed
    public ResponseEntity<PageDetails> getPageDetails(@PathVariable Long id) {
        log.debug("REST request to get PageDetails : {}", id);
        PageDetails pageDetails = pageDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pageDetails));
    }

    /**
     * DELETE  /page-details/:id : delete the "id" pageDetails.
     *
     * @param id the id of the pageDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/page-details/{id}")
    @Timed
    public ResponseEntity<Void> deletePageDetails(@PathVariable Long id) {
        log.debug("REST request to delete PageDetails : {}", id);
        pageDetailsRepository.delete(id);
        pageDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/page-details?query=:query : search for the pageDetails corresponding
     * to the query.
     *
     * @param query the query of the pageDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/page-details")
    @Timed
    public List<PageDetails> searchPageDetails(@RequestParam String query) {
        log.debug("REST request to search PageDetails for query {}", query);
        return StreamSupport
            .stream(pageDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
