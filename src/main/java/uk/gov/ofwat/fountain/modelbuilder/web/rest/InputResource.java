package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Input;

import uk.gov.ofwat.fountain.modelbuilder.repository.InputRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.InputSearchRepository;
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
 * REST controller for managing Input.
 */
@RestController
@RequestMapping("/api")
public class InputResource {

    private final Logger log = LoggerFactory.getLogger(InputResource.class);

    private static final String ENTITY_NAME = "input";
        
    private final InputRepository inputRepository;

    private final InputSearchRepository inputSearchRepository;

    public InputResource(InputRepository inputRepository, InputSearchRepository inputSearchRepository) {
        this.inputRepository = inputRepository;
        this.inputSearchRepository = inputSearchRepository;
    }

    /**
     * POST  /inputs : Create a new input.
     *
     * @param input the input to create
     * @return the ResponseEntity with status 201 (Created) and with body the new input, or with status 400 (Bad Request) if the input has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inputs")
    @Timed
    public ResponseEntity<Input> createInput(@Valid @RequestBody Input input) throws URISyntaxException {
        log.debug("REST request to save Input : {}", input);
        if (input.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new input cannot already have an ID")).body(null);
        }
        Input result = inputRepository.save(input);
        inputSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inputs : Updates an existing input.
     *
     * @param input the input to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated input,
     * or with status 400 (Bad Request) if the input is not valid,
     * or with status 500 (Internal Server Error) if the input couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inputs")
    @Timed
    public ResponseEntity<Input> updateInput(@Valid @RequestBody Input input) throws URISyntaxException {
        log.debug("REST request to update Input : {}", input);
        if (input.getId() == null) {
            return createInput(input);
        }
        Input result = inputRepository.save(input);
        inputSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, input.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inputs : get all the inputs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inputs in body
     */
    @GetMapping("/inputs")
    @Timed
    public List<Input> getAllInputs() {
        log.debug("REST request to get all Inputs");
        List<Input> inputs = inputRepository.findAll();
        return inputs;
    }

    /**
     * GET  /inputs/:id : get the "id" input.
     *
     * @param id the id of the input to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the input, or with status 404 (Not Found)
     */
    @GetMapping("/inputs/{id}")
    @Timed
    public ResponseEntity<Input> getInput(@PathVariable Long id) {
        log.debug("REST request to get Input : {}", id);
        Input input = inputRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(input));
    }

    /**
     * DELETE  /inputs/:id : delete the "id" input.
     *
     * @param id the id of the input to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inputs/{id}")
    @Timed
    public ResponseEntity<Void> deleteInput(@PathVariable Long id) {
        log.debug("REST request to delete Input : {}", id);
        inputRepository.delete(id);
        inputSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inputs?query=:query : search for the input corresponding
     * to the query.
     *
     * @param query the query of the input search 
     * @return the result of the search
     */
    @GetMapping("/_search/inputs")
    @Timed
    public List<Input> searchInputs(@RequestParam String query) {
        log.debug("REST request to search Inputs for query {}", query);
        return StreamSupport
            .stream(inputSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
