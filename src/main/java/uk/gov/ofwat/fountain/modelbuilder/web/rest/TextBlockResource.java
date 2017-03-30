package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.TextBlock;

import uk.gov.ofwat.fountain.modelbuilder.repository.TextBlockRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TextBlockSearchRepository;
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
 * REST controller for managing TextBlock.
 */
@RestController
@RequestMapping("/api")
public class TextBlockResource {

    private final Logger log = LoggerFactory.getLogger(TextBlockResource.class);

    private static final String ENTITY_NAME = "textBlock";
        
    private final TextBlockRepository textBlockRepository;

    private final TextBlockSearchRepository textBlockSearchRepository;

    public TextBlockResource(TextBlockRepository textBlockRepository, TextBlockSearchRepository textBlockSearchRepository) {
        this.textBlockRepository = textBlockRepository;
        this.textBlockSearchRepository = textBlockSearchRepository;
    }

    /**
     * POST  /text-blocks : Create a new textBlock.
     *
     * @param textBlock the textBlock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new textBlock, or with status 400 (Bad Request) if the textBlock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/text-blocks")
    @Timed
    public ResponseEntity<TextBlock> createTextBlock(@Valid @RequestBody TextBlock textBlock) throws URISyntaxException {
        log.debug("REST request to save TextBlock : {}", textBlock);
        if (textBlock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new textBlock cannot already have an ID")).body(null);
        }
        TextBlock result = textBlockRepository.save(textBlock);
        textBlockSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/text-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /text-blocks : Updates an existing textBlock.
     *
     * @param textBlock the textBlock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated textBlock,
     * or with status 400 (Bad Request) if the textBlock is not valid,
     * or with status 500 (Internal Server Error) if the textBlock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/text-blocks")
    @Timed
    public ResponseEntity<TextBlock> updateTextBlock(@Valid @RequestBody TextBlock textBlock) throws URISyntaxException {
        log.debug("REST request to update TextBlock : {}", textBlock);
        if (textBlock.getId() == null) {
            return createTextBlock(textBlock);
        }
        TextBlock result = textBlockRepository.save(textBlock);
        textBlockSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, textBlock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /text-blocks : get all the textBlocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of textBlocks in body
     */
    @GetMapping("/text-blocks")
    @Timed
    public List<TextBlock> getAllTextBlocks() {
        log.debug("REST request to get all TextBlocks");
        List<TextBlock> textBlocks = textBlockRepository.findAll();
        return textBlocks;
    }

    /**
     * GET  /text-blocks/:id : get the "id" textBlock.
     *
     * @param id the id of the textBlock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the textBlock, or with status 404 (Not Found)
     */
    @GetMapping("/text-blocks/{id}")
    @Timed
    public ResponseEntity<TextBlock> getTextBlock(@PathVariable Long id) {
        log.debug("REST request to get TextBlock : {}", id);
        TextBlock textBlock = textBlockRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(textBlock));
    }

    /**
     * DELETE  /text-blocks/:id : delete the "id" textBlock.
     *
     * @param id the id of the textBlock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/text-blocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTextBlock(@PathVariable Long id) {
        log.debug("REST request to delete TextBlock : {}", id);
        textBlockRepository.delete(id);
        textBlockSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/text-blocks?query=:query : search for the textBlock corresponding
     * to the query.
     *
     * @param query the query of the textBlock search 
     * @return the result of the search
     */
    @GetMapping("/_search/text-blocks")
    @Timed
    public List<TextBlock> searchTextBlocks(@RequestParam String query) {
        log.debug("REST request to search TextBlocks for query {}", query);
        return StreamSupport
            .stream(textBlockSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
