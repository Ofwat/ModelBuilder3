package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails;

import uk.gov.ofwat.fountain.modelbuilder.repository.LineDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.LineDetailsSearchRepository;
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
 * REST controller for managing LineDetails.
 */
@RestController
@RequestMapping("/api")
public class LineDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LineDetailsResource.class);

    private static final String ENTITY_NAME = "lineDetails";
        
    private final LineDetailsRepository lineDetailsRepository;

    private final LineDetailsSearchRepository lineDetailsSearchRepository;

    public LineDetailsResource(LineDetailsRepository lineDetailsRepository, LineDetailsSearchRepository lineDetailsSearchRepository) {
        this.lineDetailsRepository = lineDetailsRepository;
        this.lineDetailsSearchRepository = lineDetailsSearchRepository;
    }

    /**
     * POST  /line-details : Create a new lineDetails.
     *
     * @param lineDetails the lineDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lineDetails, or with status 400 (Bad Request) if the lineDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/line-details")
    @Timed
    public ResponseEntity<LineDetails> createLineDetails(@RequestBody LineDetails lineDetails) throws URISyntaxException {
        log.debug("REST request to save LineDetails : {}", lineDetails);
        if (lineDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lineDetails cannot already have an ID")).body(null);
        }
        LineDetails result = lineDetailsRepository.save(lineDetails);
        lineDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/line-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /line-details : Updates an existing lineDetails.
     *
     * @param lineDetails the lineDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lineDetails,
     * or with status 400 (Bad Request) if the lineDetails is not valid,
     * or with status 500 (Internal Server Error) if the lineDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/line-details")
    @Timed
    public ResponseEntity<LineDetails> updateLineDetails(@RequestBody LineDetails lineDetails) throws URISyntaxException {
        log.debug("REST request to update LineDetails : {}", lineDetails);
        if (lineDetails.getId() == null) {
            return createLineDetails(lineDetails);
        }
        LineDetails result = lineDetailsRepository.save(lineDetails);
        lineDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lineDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /line-details : get all the lineDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lineDetails in body
     */
    @GetMapping("/line-details")
    @Timed
    public List<LineDetails> getAllLineDetails() {
        log.debug("REST request to get all LineDetails");
        List<LineDetails> lineDetails = lineDetailsRepository.findAll();
        return lineDetails;
    }

    /**
     * GET  /line-details/:id : get the "id" lineDetails.
     *
     * @param id the id of the lineDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lineDetails, or with status 404 (Not Found)
     */
    @GetMapping("/line-details/{id}")
    @Timed
    public ResponseEntity<LineDetails> getLineDetails(@PathVariable Long id) {
        log.debug("REST request to get LineDetails : {}", id);
        LineDetails lineDetails = lineDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lineDetails));
    }

    /**
     * DELETE  /line-details/:id : delete the "id" lineDetails.
     *
     * @param id the id of the lineDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/line-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteLineDetails(@PathVariable Long id) {
        log.debug("REST request to delete LineDetails : {}", id);
        lineDetailsRepository.delete(id);
        lineDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/line-details?query=:query : search for the lineDetails corresponding
     * to the query.
     *
     * @param query the query of the lineDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/line-details")
    @Timed
    public List<LineDetails> searchLineDetails(@RequestParam String query) {
        log.debug("REST request to search LineDetails for query {}", query);
        return StreamSupport
            .stream(lineDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
