package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditChange;
import uk.gov.ofwat.fountain.modelbuilder.repository.AuditChangeRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.AuditChangeSearchRepository;
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
 * Test class for the AuditChangeResource REST controller.
 *
 * @see AuditChangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class AuditChangeResourceIntTest {

    private static final String DEFAULT_CHANGE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_TEXT = "BBBBBBBBBB";

    @Autowired
    private AuditChangeRepository auditChangeRepository;

    @Autowired
    private AuditChangeSearchRepository auditChangeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuditChangeMockMvc;

    private AuditChange auditChange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AuditChangeResource auditChangeResource = new AuditChangeResource(auditChangeRepository, auditChangeSearchRepository);
        this.restAuditChangeMockMvc = MockMvcBuilders.standaloneSetup(auditChangeResource)
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
    public static AuditChange createEntity(EntityManager em) {
        AuditChange auditChange = new AuditChange()
                .changeText(DEFAULT_CHANGE_TEXT);
        return auditChange;
    }

    @Before
    public void initTest() {
        auditChangeSearchRepository.deleteAll();
        auditChange = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditChange() throws Exception {
        int databaseSizeBeforeCreate = auditChangeRepository.findAll().size();

        // Create the AuditChange

        restAuditChangeMockMvc.perform(post("/api/audit-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditChange)))
            .andExpect(status().isCreated());

        // Validate the AuditChange in the database
        List<AuditChange> auditChangeList = auditChangeRepository.findAll();
        assertThat(auditChangeList).hasSize(databaseSizeBeforeCreate + 1);
        AuditChange testAuditChange = auditChangeList.get(auditChangeList.size() - 1);
        assertThat(testAuditChange.getChangeText()).isEqualTo(DEFAULT_CHANGE_TEXT);

        // Validate the AuditChange in Elasticsearch
        AuditChange auditChangeEs = auditChangeSearchRepository.findOne(testAuditChange.getId());
        assertThat(auditChangeEs).isEqualToComparingFieldByField(testAuditChange);
    }

    @Test
    @Transactional
    public void createAuditChangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditChangeRepository.findAll().size();

        // Create the AuditChange with an existing ID
        AuditChange existingAuditChange = new AuditChange();
        existingAuditChange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditChangeMockMvc.perform(post("/api/audit-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAuditChange)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AuditChange> auditChangeList = auditChangeRepository.findAll();
        assertThat(auditChangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuditChanges() throws Exception {
        // Initialize the database
        auditChangeRepository.saveAndFlush(auditChange);

        // Get all the auditChangeList
        restAuditChangeMockMvc.perform(get("/api/audit-changes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeText").value(hasItem(DEFAULT_CHANGE_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getAuditChange() throws Exception {
        // Initialize the database
        auditChangeRepository.saveAndFlush(auditChange);

        // Get the auditChange
        restAuditChangeMockMvc.perform(get("/api/audit-changes/{id}", auditChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditChange.getId().intValue()))
            .andExpect(jsonPath("$.changeText").value(DEFAULT_CHANGE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuditChange() throws Exception {
        // Get the auditChange
        restAuditChangeMockMvc.perform(get("/api/audit-changes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditChange() throws Exception {
        // Initialize the database
        auditChangeRepository.saveAndFlush(auditChange);
        auditChangeSearchRepository.save(auditChange);
        int databaseSizeBeforeUpdate = auditChangeRepository.findAll().size();

        // Update the auditChange
        AuditChange updatedAuditChange = auditChangeRepository.findOne(auditChange.getId());
        updatedAuditChange
                .changeText(UPDATED_CHANGE_TEXT);

        restAuditChangeMockMvc.perform(put("/api/audit-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditChange)))
            .andExpect(status().isOk());

        // Validate the AuditChange in the database
        List<AuditChange> auditChangeList = auditChangeRepository.findAll();
        assertThat(auditChangeList).hasSize(databaseSizeBeforeUpdate);
        AuditChange testAuditChange = auditChangeList.get(auditChangeList.size() - 1);
        assertThat(testAuditChange.getChangeText()).isEqualTo(UPDATED_CHANGE_TEXT);

        // Validate the AuditChange in Elasticsearch
        AuditChange auditChangeEs = auditChangeSearchRepository.findOne(testAuditChange.getId());
        assertThat(auditChangeEs).isEqualToComparingFieldByField(testAuditChange);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditChange() throws Exception {
        int databaseSizeBeforeUpdate = auditChangeRepository.findAll().size();

        // Create the AuditChange

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuditChangeMockMvc.perform(put("/api/audit-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditChange)))
            .andExpect(status().isCreated());

        // Validate the AuditChange in the database
        List<AuditChange> auditChangeList = auditChangeRepository.findAll();
        assertThat(auditChangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuditChange() throws Exception {
        // Initialize the database
        auditChangeRepository.saveAndFlush(auditChange);
        auditChangeSearchRepository.save(auditChange);
        int databaseSizeBeforeDelete = auditChangeRepository.findAll().size();

        // Get the auditChange
        restAuditChangeMockMvc.perform(delete("/api/audit-changes/{id}", auditChange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auditChangeExistsInEs = auditChangeSearchRepository.exists(auditChange.getId());
        assertThat(auditChangeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuditChange> auditChangeList = auditChangeRepository.findAll();
        assertThat(auditChangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuditChange() throws Exception {
        // Initialize the database
        auditChangeRepository.saveAndFlush(auditChange);
        auditChangeSearchRepository.save(auditChange);

        // Search the auditChange
        restAuditChangeMockMvc.perform(get("/api/_search/audit-changes?query=id:" + auditChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeText").value(hasItem(DEFAULT_CHANGE_TEXT.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditChange.class);
    }
}
