package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Heading;

import uk.gov.ofwat.fountain.modelbuilder.repository.HeadingRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.HeadingSearchRepository;
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
 * REST controller for managing Heading.
 */
@RestController
@RequestMapping("/api")
public class HeadingResource {

    private final Logger log = LoggerFactory.getLogger(HeadingResource.class);

    private static final String ENTITY_NAME = "heading";
        
    private final HeadingRepository headingRepository;

    private final HeadingSearchRepository headingSearchRepository;

    public HeadingResource(HeadingRepository headingRepository, HeadingSearchRepository headingSearchRepository) {
        this.headingRepository = headingRepository;
        this.headingSearchRepository = headingSearchRepository;
    }

    /**
     * POST  /headings : Create a new heading.
     *
     * @param heading the heading to create
     * @return the ResponseEntity with status 201 (Created) and with body the new heading, or with status 400 (Bad Request) if the heading has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/headings")
    @Timed
    public ResponseEntity<Heading> createHeading(@Valid @RequestBody Heading heading) throws URISyntaxException {
        log.debug("REST request to save Heading : {}", heading);
        if (heading.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new heading cannot already have an ID")).body(null);
        }
        Heading result = headingRepository.save(heading);
        headingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/headings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /headings : Updates an existing heading.
     *
     * @param heading the heading to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated heading,
     * or with status 400 (Bad Request) if the heading is not valid,
     * or with status 500 (Internal Server Error) if the heading couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/headings")
    @Timed
    public ResponseEntity<Heading> updateHeading(@Valid @RequestBody Heading heading) throws URISyntaxException {
        log.debug("REST request to update Heading : {}", heading);
        if (heading.getId() == null) {
            return createHeading(heading);
        }
        Heading result = headingRepository.save(heading);
        headingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, heading.getId().toString()))
            .body(result);
    }

    /**
     * GET  /headings : get all the headings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of headings in body
     */
    @GetMapping("/headings")
    @Timed
    public List<Heading> getAllHeadings() {
        log.debug("REST request to get all Headings");
        List<Heading> headings = headingRepository.findAll();
        return headings;
    }

    /**
     * GET  /headings/:id : get the "id" heading.
     *
     * @param id the id of the heading to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the heading, or with status 404 (Not Found)
     */
    @GetMapping("/headings/{id}")
    @Timed
    public ResponseEntity<Heading> getHeading(@PathVariable Long id) {
        log.debug("REST request to get Heading : {}", id);
        Heading heading = headingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(heading));
    }

    /**
     * DELETE  /headings/:id : delete the "id" heading.
     *
     * @param id the id of the heading to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/headings/{id}")
    @Timed
    public ResponseEntity<Void> deleteHeading(@PathVariable Long id) {
        log.debug("REST request to delete Heading : {}", id);
        headingRepository.delete(id);
        headingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/headings?query=:query : search for the heading corresponding
     * to the query.
     *
     * @param query the query of the heading search 
     * @return the result of the search
     */
    @GetMapping("/_search/headings")
    @Timed
    public List<Heading> searchHeadings(@RequestParam String query) {
        log.debug("REST request to search Headings for query {}", query);
        return StreamSupport
            .stream(headingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
