package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.SectionDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.SectionDetailsSearchRepository;
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
 * REST controller for managing SectionDetails.
 */
@RestController
@RequestMapping("/api")
public class SectionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SectionDetailsResource.class);

    private static final String ENTITY_NAME = "sectionDetails";
        
    private final SectionDetailsRepository sectionDetailsRepository;

    private final SectionDetailsSearchRepository sectionDetailsSearchRepository;

    public SectionDetailsResource(SectionDetailsRepository sectionDetailsRepository, SectionDetailsSearchRepository sectionDetailsSearchRepository) {
        this.sectionDetailsRepository = sectionDetailsRepository;
        this.sectionDetailsSearchRepository = sectionDetailsSearchRepository;
    }

    /**
     * POST  /section-details : Create a new sectionDetails.
     *
     * @param sectionDetails the sectionDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionDetails, or with status 400 (Bad Request) if the sectionDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/section-details")
    @Timed
    public ResponseEntity<SectionDetails> createSectionDetails(@Valid @RequestBody SectionDetails sectionDetails) throws URISyntaxException {
        log.debug("REST request to save SectionDetails : {}", sectionDetails);
        if (sectionDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sectionDetails cannot already have an ID")).body(null);
        }
        SectionDetails result = sectionDetailsRepository.save(sectionDetails);
        sectionDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/section-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /section-details : Updates an existing sectionDetails.
     *
     * @param sectionDetails the sectionDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionDetails,
     * or with status 400 (Bad Request) if the sectionDetails is not valid,
     * or with status 500 (Internal Server Error) if the sectionDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/section-details")
    @Timed
    public ResponseEntity<SectionDetails> updateSectionDetails(@Valid @RequestBody SectionDetails sectionDetails) throws URISyntaxException {
        log.debug("REST request to update SectionDetails : {}", sectionDetails);
        if (sectionDetails.getId() == null) {
            return createSectionDetails(sectionDetails);
        }
        SectionDetails result = sectionDetailsRepository.save(sectionDetails);
        sectionDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /section-details : get all the sectionDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sectionDetails in body
     */
    @GetMapping("/section-details")
    @Timed
    public List<SectionDetails> getAllSectionDetails() {
        log.debug("REST request to get all SectionDetails");
        List<SectionDetails> sectionDetails = sectionDetailsRepository.findAll();
        return sectionDetails;
    }

    /**
     * GET  /section-details/:id : get the "id" sectionDetails.
     *
     * @param id the id of the sectionDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionDetails, or with status 404 (Not Found)
     */
    @GetMapping("/section-details/{id}")
    @Timed
    public ResponseEntity<SectionDetails> getSectionDetails(@PathVariable Long id) {
        log.debug("REST request to get SectionDetails : {}", id);
        SectionDetails sectionDetails = sectionDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionDetails));
    }

    /**
     * DELETE  /section-details/:id : delete the "id" sectionDetails.
     *
     * @param id the id of the sectionDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/section-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteSectionDetails(@PathVariable Long id) {
        log.debug("REST request to delete SectionDetails : {}", id);
        sectionDetailsRepository.delete(id);
        sectionDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/section-details?query=:query : search for the sectionDetails corresponding
     * to the query.
     *
     * @param query the query of the sectionDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/section-details")
    @Timed
    public List<SectionDetails> searchSectionDetails(@RequestParam String query) {
        log.debug("REST request to search SectionDetails for query {}", query);
        return StreamSupport
            .stream(sectionDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
