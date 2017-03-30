package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Text;

import uk.gov.ofwat.fountain.modelbuilder.repository.TextRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TextSearchRepository;
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
 * REST controller for managing Text.
 */
@RestController
@RequestMapping("/api")
public class TextResource {

    private final Logger log = LoggerFactory.getLogger(TextResource.class);

    private static final String ENTITY_NAME = "text";
        
    private final TextRepository textRepository;

    private final TextSearchRepository textSearchRepository;

    public TextResource(TextRepository textRepository, TextSearchRepository textSearchRepository) {
        this.textRepository = textRepository;
        this.textSearchRepository = textSearchRepository;
    }

    /**
     * POST  /texts : Create a new text.
     *
     * @param text the text to create
     * @return the ResponseEntity with status 201 (Created) and with body the new text, or with status 400 (Bad Request) if the text has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/texts")
    @Timed
    public ResponseEntity<Text> createText(@Valid @RequestBody Text text) throws URISyntaxException {
        log.debug("REST request to save Text : {}", text);
        if (text.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new text cannot already have an ID")).body(null);
        }
        Text result = textRepository.save(text);
        textSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /texts : Updates an existing text.
     *
     * @param text the text to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated text,
     * or with status 400 (Bad Request) if the text is not valid,
     * or with status 500 (Internal Server Error) if the text couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/texts")
    @Timed
    public ResponseEntity<Text> updateText(@Valid @RequestBody Text text) throws URISyntaxException {
        log.debug("REST request to update Text : {}", text);
        if (text.getId() == null) {
            return createText(text);
        }
        Text result = textRepository.save(text);
        textSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, text.getId().toString()))
            .body(result);
    }

    /**
     * GET  /texts : get all the texts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of texts in body
     */
    @GetMapping("/texts")
    @Timed
    public List<Text> getAllTexts() {
        log.debug("REST request to get all Texts");
        List<Text> texts = textRepository.findAll();
        return texts;
    }

    /**
     * GET  /texts/:id : get the "id" text.
     *
     * @param id the id of the text to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the text, or with status 404 (Not Found)
     */
    @GetMapping("/texts/{id}")
    @Timed
    public ResponseEntity<Text> getText(@PathVariable Long id) {
        log.debug("REST request to get Text : {}", id);
        Text text = textRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(text));
    }

    /**
     * DELETE  /texts/:id : delete the "id" text.
     *
     * @param id the id of the text to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/texts/{id}")
    @Timed
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        log.debug("REST request to delete Text : {}", id);
        textRepository.delete(id);
        textSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/texts?query=:query : search for the text corresponding
     * to the query.
     *
     * @param query the query of the text search 
     * @return the result of the search
     */
    @GetMapping("/_search/texts")
    @Timed
    public List<Text> searchTexts(@RequestParam String query) {
        log.debug("REST request to search Texts for query {}", query);
        return StreamSupport
            .stream(textSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
