package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.Macro;

import uk.gov.ofwat.fountain.modelbuilder.repository.MacroRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.MacroSearchRepository;
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
 * REST controller for managing Macro.
 */
@RestController
@RequestMapping("/api")
public class MacroResource {

    private final Logger log = LoggerFactory.getLogger(MacroResource.class);

    private static final String ENTITY_NAME = "macro";
        
    private final MacroRepository macroRepository;

    private final MacroSearchRepository macroSearchRepository;

    public MacroResource(MacroRepository macroRepository, MacroSearchRepository macroSearchRepository) {
        this.macroRepository = macroRepository;
        this.macroSearchRepository = macroSearchRepository;
    }

    /**
     * POST  /macros : Create a new macro.
     *
     * @param macro the macro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new macro, or with status 400 (Bad Request) if the macro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/macros")
    @Timed
    public ResponseEntity<Macro> createMacro(@Valid @RequestBody Macro macro) throws URISyntaxException {
        log.debug("REST request to save Macro : {}", macro);
        if (macro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new macro cannot already have an ID")).body(null);
        }
        Macro result = macroRepository.save(macro);
        macroSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/macros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /macros : Updates an existing macro.
     *
     * @param macro the macro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated macro,
     * or with status 400 (Bad Request) if the macro is not valid,
     * or with status 500 (Internal Server Error) if the macro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/macros")
    @Timed
    public ResponseEntity<Macro> updateMacro(@Valid @RequestBody Macro macro) throws URISyntaxException {
        log.debug("REST request to update Macro : {}", macro);
        if (macro.getId() == null) {
            return createMacro(macro);
        }
        Macro result = macroRepository.save(macro);
        macroSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, macro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /macros : get all the macros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of macros in body
     */
    @GetMapping("/macros")
    @Timed
    public List<Macro> getAllMacros() {
        log.debug("REST request to get all Macros");
        List<Macro> macros = macroRepository.findAll();
        return macros;
    }

    /**
     * GET  /macros/:id : get the "id" macro.
     *
     * @param id the id of the macro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the macro, or with status 404 (Not Found)
     */
    @GetMapping("/macros/{id}")
    @Timed
    public ResponseEntity<Macro> getMacro(@PathVariable Long id) {
        log.debug("REST request to get Macro : {}", id);
        Macro macro = macroRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(macro));
    }

    /**
     * DELETE  /macros/:id : delete the "id" macro.
     *
     * @param id the id of the macro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/macros/{id}")
    @Timed
    public ResponseEntity<Void> deleteMacro(@PathVariable Long id) {
        log.debug("REST request to delete Macro : {}", id);
        macroRepository.delete(id);
        macroSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/macros?query=:query : search for the macro corresponding
     * to the query.
     *
     * @param query the query of the macro search 
     * @return the result of the search
     */
    @GetMapping("/_search/macros")
    @Timed
    public List<Macro> searchMacros(@RequestParam String query) {
        log.debug("REST request to search Macros for query {}", query);
        return StreamSupport
            .stream(macroSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
