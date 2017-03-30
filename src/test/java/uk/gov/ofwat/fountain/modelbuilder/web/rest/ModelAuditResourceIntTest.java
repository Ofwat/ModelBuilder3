package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelAuditRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ModelAuditSearchRepository;
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
 * Test class for the ModelAuditResource REST controller.
 *
 * @see ModelAuditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class ModelAuditResourceIntTest {

    @Autowired
    private ModelAuditRepository modelAuditRepository;

    @Autowired
    private ModelAuditSearchRepository modelAuditSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModelAuditMockMvc;

    private ModelAudit modelAudit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ModelAuditResource modelAuditResource = new ModelAuditResource(modelAuditRepository, modelAuditSearchRepository);
        this.restModelAuditMockMvc = MockMvcBuilders.standaloneSetup(modelAuditResource)
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
    public static ModelAudit createEntity(EntityManager em) {
        ModelAudit modelAudit = new ModelAudit();
        return modelAudit;
    }

    @Before
    public void initTest() {
        modelAuditSearchRepository.deleteAll();
        modelAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelAudit() throws Exception {
        int databaseSizeBeforeCreate = modelAuditRepository.findAll().size();

        // Create the ModelAudit

        restModelAuditMockMvc.perform(post("/api/model-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelAudit)))
            .andExpect(status().isCreated());

        // Validate the ModelAudit in the database
        List<ModelAudit> modelAuditList = modelAuditRepository.findAll();
        assertThat(modelAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ModelAudit testModelAudit = modelAuditList.get(modelAuditList.size() - 1);

        // Validate the ModelAudit in Elasticsearch
        ModelAudit modelAuditEs = modelAuditSearchRepository.findOne(testModelAudit.getId());
        assertThat(modelAuditEs).isEqualToComparingFieldByField(testModelAudit);
    }

    @Test
    @Transactional
    public void createModelAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelAuditRepository.findAll().size();

        // Create the ModelAudit with an existing ID
        ModelAudit existingModelAudit = new ModelAudit();
        existingModelAudit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelAuditMockMvc.perform(post("/api/model-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModelAudit)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModelAudit> modelAuditList = modelAuditRepository.findAll();
        assertThat(modelAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModelAudits() throws Exception {
        // Initialize the database
        modelAuditRepository.saveAndFlush(modelAudit);

        // Get all the modelAuditList
        restModelAuditMockMvc.perform(get("/api/model-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelAudit.getId().intValue())));
    }

    @Test
    @Transactional
    public void getModelAudit() throws Exception {
        // Initialize the database
        modelAuditRepository.saveAndFlush(modelAudit);

        // Get the modelAudit
        restModelAuditMockMvc.perform(get("/api/model-audits/{id}", modelAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelAudit.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModelAudit() throws Exception {
        // Get the modelAudit
        restModelAuditMockMvc.perform(get("/api/model-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelAudit() throws Exception {
        // Initialize the database
        modelAuditRepository.saveAndFlush(modelAudit);
        modelAuditSearchRepository.save(modelAudit);
        int databaseSizeBeforeUpdate = modelAuditRepository.findAll().size();

        // Update the modelAudit
        ModelAudit updatedModelAudit = modelAuditRepository.findOne(modelAudit.getId());

        restModelAuditMockMvc.perform(put("/api/model-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelAudit)))
            .andExpect(status().isOk());

        // Validate the ModelAudit in the database
        List<ModelAudit> modelAuditList = modelAuditRepository.findAll();
        assertThat(modelAuditList).hasSize(databaseSizeBeforeUpdate);
        ModelAudit testModelAudit = modelAuditList.get(modelAuditList.size() - 1);

        // Validate the ModelAudit in Elasticsearch
        ModelAudit modelAuditEs = modelAuditSearchRepository.findOne(testModelAudit.getId());
        assertThat(modelAuditEs).isEqualToComparingFieldByField(testModelAudit);
    }

    @Test
    @Transactional
    public void updateNonExistingModelAudit() throws Exception {
        int databaseSizeBeforeUpdate = modelAuditRepository.findAll().size();

        // Create the ModelAudit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelAuditMockMvc.perform(put("/api/model-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelAudit)))
            .andExpect(status().isCreated());

        // Validate the ModelAudit in the database
        List<ModelAudit> modelAuditList = modelAuditRepository.findAll();
        assertThat(modelAuditList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelAudit() throws Exception {
        // Initialize the database
        modelAuditRepository.saveAndFlush(modelAudit);
        modelAuditSearchRepository.save(modelAudit);
        int databaseSizeBeforeDelete = modelAuditRepository.findAll().size();

        // Get the modelAudit
        restModelAuditMockMvc.perform(delete("/api/model-audits/{id}", modelAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modelAuditExistsInEs = modelAuditSearchRepository.exists(modelAudit.getId());
        assertThat(modelAuditExistsInEs).isFalse();

        // Validate the database is empty
        List<ModelAudit> modelAuditList = modelAuditRepository.findAll();
        assertThat(modelAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModelAudit() throws Exception {
        // Initialize the database
        modelAuditRepository.saveAndFlush(modelAudit);
        modelAuditSearchRepository.save(modelAudit);

        // Search the modelAudit
        restModelAuditMockMvc.perform(get("/api/_search/model-audits?query=id:" + modelAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelAudit.getId().intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelAudit.class);
    }
}
