package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage;

import uk.gov.ofwat.fountain.modelbuilder.repository.CompanyPageRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.CompanyPageSearchRepository;
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
 * REST controller for managing CompanyPage.
 */
@RestController
@RequestMapping("/api")
public class CompanyPageResource {

    private final Logger log = LoggerFactory.getLogger(CompanyPageResource.class);

    private static final String ENTITY_NAME = "companyPage";
        
    private final CompanyPageRepository companyPageRepository;

    private final CompanyPageSearchRepository companyPageSearchRepository;

    public CompanyPageResource(CompanyPageRepository companyPageRepository, CompanyPageSearchRepository companyPageSearchRepository) {
        this.companyPageRepository = companyPageRepository;
        this.companyPageSearchRepository = companyPageSearchRepository;
    }

    /**
     * POST  /company-pages : Create a new companyPage.
     *
     * @param companyPage the companyPage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyPage, or with status 400 (Bad Request) if the companyPage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-pages")
    @Timed
    public ResponseEntity<CompanyPage> createCompanyPage(@Valid @RequestBody CompanyPage companyPage) throws URISyntaxException {
        log.debug("REST request to save CompanyPage : {}", companyPage);
        if (companyPage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyPage cannot already have an ID")).body(null);
        }
        CompanyPage result = companyPageRepository.save(companyPage);
        companyPageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/company-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-pages : Updates an existing companyPage.
     *
     * @param companyPage the companyPage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyPage,
     * or with status 400 (Bad Request) if the companyPage is not valid,
     * or with status 500 (Internal Server Error) if the companyPage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-pages")
    @Timed
    public ResponseEntity<CompanyPage> updateCompanyPage(@Valid @RequestBody CompanyPage companyPage) throws URISyntaxException {
        log.debug("REST request to update CompanyPage : {}", companyPage);
        if (companyPage.getId() == null) {
            return createCompanyPage(companyPage);
        }
        CompanyPage result = companyPageRepository.save(companyPage);
        companyPageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyPage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-pages : get all the companyPages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyPages in body
     */
    @GetMapping("/company-pages")
    @Timed
    public List<CompanyPage> getAllCompanyPages() {
        log.debug("REST request to get all CompanyPages");
        List<CompanyPage> companyPages = companyPageRepository.findAll();
        return companyPages;
    }

    /**
     * GET  /company-pages/:id : get the "id" companyPage.
     *
     * @param id the id of the companyPage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyPage, or with status 404 (Not Found)
     */
    @GetMapping("/company-pages/{id}")
    @Timed
    public ResponseEntity<CompanyPage> getCompanyPage(@PathVariable Long id) {
        log.debug("REST request to get CompanyPage : {}", id);
        CompanyPage companyPage = companyPageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyPage));
    }

    /**
     * DELETE  /company-pages/:id : delete the "id" companyPage.
     *
     * @param id the id of the companyPage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-pages/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyPage(@PathVariable Long id) {
        log.debug("REST request to delete CompanyPage : {}", id);
        companyPageRepository.delete(id);
        companyPageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-pages?query=:query : search for the companyPage corresponding
     * to the query.
     *
     * @param query the query of the companyPage search 
     * @return the result of the search
     */
    @GetMapping("/_search/company-pages")
    @Timed
    public List<CompanyPage> searchCompanyPages(@RequestParam String query) {
        log.debug("REST request to search CompanyPages for query {}", query);
        return StreamSupport
            .stream(companyPageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
