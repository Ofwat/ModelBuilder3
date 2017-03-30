package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails;
import uk.gov.ofwat.fountain.modelbuilder.repository.AuditDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.AuditDetailsSearchRepository;
import uk.gov.ofwat.fountain.modelbuilder.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuditDetailsResource REST controller.
 *
 * @see AuditDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class AuditDetailsResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private AuditDetailsRepository auditDetailsRepository;

    @Autowired
    private AuditDetailsSearchRepository auditDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuditDetailsMockMvc;

    private AuditDetails auditDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AuditDetailsResource auditDetailsResource = new AuditDetailsResource(auditDetailsRepository, auditDetailsSearchRepository);
        this.restAuditDetailsMockMvc = MockMvcBuilders.standaloneSetup(auditDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditDetails createEntity(EntityManager em) {
        AuditDetails auditDetails = new AuditDetails()
                .username(DEFAULT_USERNAME)
                .timestamp(DEFAULT_TIMESTAMP)
                .reason(DEFAULT_REASON);
        return auditDetails;
    }

    @Before
    public void initTest() {
        auditDetailsSearchRepository.deleteAll();
        auditDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditDetails() throws Exception {
        int databaseSizeBeforeCreate = auditDetailsRepository.findAll().size();

        // Create the AuditDetails

        restAuditDetailsMockMvc.perform(post("/api/audit-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditDetails)))
            .andExpect(status().isCreated());

        // Validate the AuditDetails in the database
        List<AuditDetails> auditDetailsList = auditDetailsRepository.findAll();
        assertThat(auditDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        AuditDetails testAuditDetails = auditDetailsList.get(auditDetailsList.size() - 1);
        assertThat(testAuditDetails.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testAuditDetails.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testAuditDetails.getReason()).isEqualTo(DEFAULT_REASON);

        // Validate the AuditDetails in Elasticsearch
        AuditDetails auditDetailsEs = auditDetailsSearchRepository.findOne(testAuditDetails.getId());
        assertThat(auditDetailsEs).isEqualToComparingFieldByField(testAuditDetails);
    }

    @Test
    @Transactional
    public void createAuditDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditDetailsRepository.findAll().size();

        // Create the AuditDetails with an existing ID
        AuditDetails existingAuditDetails = new AuditDetails();
        existingAuditDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditDetailsMockMvc.perform(post("/api/audit-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAuditDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AuditDetails> auditDetailsList = auditDetailsRepository.findAll();
        assertThat(auditDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuditDetails() throws Exception {
        // Initialize the database
        auditDetailsRepository.saveAndFlush(auditDetails);

        // Get all the auditDetailsList
        restAuditDetailsMockMvc.perform(get("/api/audit-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getAuditDetails() throws Exception {
        // Initialize the database
        auditDetailsRepository.saveAndFlush(auditDetails);

        // Get the auditDetails
        restAuditDetailsMockMvc.perform(get("/api/audit-details/{id}", auditDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditDetails.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuditDetails() throws Exception {
        // Get the auditDetails
        restAuditDetailsMockMvc.perform(get("/api/audit-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditDetails() throws Exception {
        // Initialize the database
        auditDetailsRepository.saveAndFlush(auditDetails);
        auditDetailsSearchRepository.save(auditDetails);
        int databaseSizeBeforeUpdate = auditDetailsRepository.findAll().size();

        // Update the auditDetails
        AuditDetails updatedAuditDetails = auditDetailsRepository.findOne(auditDetails.getId());
        updatedAuditDetails
                .username(UPDATED_USERNAME)
                .timestamp(UPDATED_TIMESTAMP)
                .reason(UPDATED_REASON);

        restAuditDetailsMockMvc.perform(put("/api/audit-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditDetails)))
            .andExpect(status().isOk());

        // Validate the AuditDetails in the database
        List<AuditDetails> auditDetailsList = auditDetailsRepository.findAll();
        assertThat(auditDetailsList).hasSize(databaseSizeBeforeUpdate);
        AuditDetails testAuditDetails = auditDetailsList.get(auditDetailsList.size() - 1);
        assertThat(testAuditDetails.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testAuditDetails.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testAuditDetails.getReason()).isEqualTo(UPDATED_REASON);

        // Validate the AuditDetails in Elasticsearch
        AuditDetails auditDetailsEs = auditDetailsSearchRepository.findOne(testAuditDetails.getId());
        assertThat(auditDetailsEs).isEqualToComparingFieldByField(testAuditDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditDetails() throws Exception {
        int databaseSizeBeforeUpdate = auditDetailsRepository.findAll().size();

        // Create the AuditDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuditDetailsMockMvc.perform(put("/api/audit-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditDetails)))
            .andExpect(status().isCreated());

        // Validate the AuditDetails in the database
        List<AuditDetails> auditDetailsList = auditDetailsRepository.findAll();
        assertThat(auditDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuditDetails() throws Exception {
        // Initialize the database
        auditDetailsRepository.saveAndFlush(auditDetails);
        auditDetailsSearchRepository.save(auditDetails);
        int databaseSizeBeforeDelete = auditDetailsRepository.findAll().size();

        // Get the auditDetails
        restAuditDetailsMockMvc.perform(delete("/api/audit-details/{id}", auditDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auditDetailsExistsInEs = auditDetailsSearchRepository.exists(auditDetails.getId());
        assertThat(auditDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<AuditDetails> auditDetailsList = auditDetailsRepository.findAll();
        assertThat(auditDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuditDetails() throws Exception {
        // Initialize the database
        auditDetailsRepository.saveAndFlush(auditDetails);
        auditDetailsSearchRepository.save(auditDetails);

        // Search the auditDetails
        restAuditDetailsMockMvc.perform(get("/api/_search/audit-details?query=id:" + auditDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditDetails.class);
    }
}
