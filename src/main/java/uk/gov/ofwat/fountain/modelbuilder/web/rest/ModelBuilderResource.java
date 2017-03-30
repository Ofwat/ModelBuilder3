package uk.gov.ofwat.fountain.modelbuilder.web.rest;
/*
 *  Copyright (C) 2016 Water Services Regulation Authority (Ofwat)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails;
import uk.gov.ofwat.fountain.modelbuilder.repository.AuditDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.AuditDetailsSearchRepository;
import uk.gov.ofwat.fountain.modelbuilder.web.rest.util.HeaderUtil;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


/**
 * REST controller for managing AuditDetails.
 */
@RestController
@RequestMapping("/api")
public class ModelBuilderResource {

    private final Logger log = LoggerFactory.getLogger(AuditDetailsResource.class);

    @Inject
    private AuditDetailsRepository auditDetailsRepository;

    @Inject
    private AuditDetailsSearchRepository auditDetailsSearchRepository;

    /**
     * POST  /auditDetailss -> Create a new auditDetails.
     */

    @RequestMapping(value = "/modelBuilder",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditDetails> createAuditDetails(@RequestBody AuditDetails auditDetails) throws URISyntaxException {
        log.debug("REST request to save AuditDetails : {}", auditDetails);
        if (auditDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("auditDetails", "idexists", "A new auditDetails cannot already have an ID")).body(null);
        }
        AuditDetails result = auditDetailsRepository.save(auditDetails);
        auditDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/auditDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("auditDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auditDetailss -> Updates an existing auditDetails.
     */
    @RequestMapping(value = "/modelBuilder",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditDetails> updateAuditDetails(@RequestBody AuditDetails auditDetails) throws URISyntaxException {
        log.debug("REST request to update AuditDetails : {}", auditDetails);
        if (auditDetails.getId() == null) {
            return createAuditDetails(auditDetails);
        }
        AuditDetails result = auditDetailsRepository.save(auditDetails);
        auditDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("auditDetails", auditDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auditDetailss -> get all the auditDetailss.
     */
    @RequestMapping(value = "/modelBuilder",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuditDetails> getAllAuditDetailss() {
        log.debug("REST request to get all AuditDetailss");
        return auditDetailsRepository.findAll();
    }

    /**
     * GET  /auditDetailss/:id -> get the "id" auditDetails.
     */
    @RequestMapping(value = "/modelBuilder/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditDetails> getAuditDetails(@PathVariable Long id) {
        log.debug("REST request to get AuditDetails : {}", id);
        AuditDetails auditDetails = auditDetailsRepository.findOne(id);
        return Optional.ofNullable(auditDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /auditDetailss/:id -> delete the "id" auditDetails.
     */
    @RequestMapping(value = "/modelBuilder/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuditDetails(@PathVariable Long id) {
        log.debug("REST request to delete AuditDetails : {}", id);
        auditDetailsRepository.delete(id);
        auditDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("auditDetails", id.toString())).build();
    }

    /**
     * SEARCH  /_search/auditDetailss/:query -> search for the auditDetails corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/modelBuilder/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuditDetails> searchAuditDetailss(@PathVariable String query) {
        log.debug("REST request to search AuditDetailss for query {}", query);
        return StreamSupport
            .stream(auditDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
