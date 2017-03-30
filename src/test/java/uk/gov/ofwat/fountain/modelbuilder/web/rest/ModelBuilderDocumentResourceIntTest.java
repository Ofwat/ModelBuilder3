package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelBuilderDocument;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelBuilderDocumentRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ModelBuilderDocumentSearchRepository;
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
 * Test class for the ModelBuilderDocumentResource REST controller.
 *
 * @see ModelBuilderDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class ModelBuilderDocumentResourceIntTest {

    private static final String DEFAULT_REPORTER = "AAAAAAAAAA";
    private static final String UPDATED_REPORTER = "BBBBBBBBBB";

    private static final String DEFAULT_AUDITOR = "AAAAAAAAAA";
    private static final String UPDATED_AUDITOR = "BBBBBBBBBB";

    @Autowired
    private ModelBuilderDocumentRepository modelBuilderDocumentRepository;

    @Autowired
    private ModelBuilderDocumentSearchRepository modelBuilderDocumentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModelBuilderDocumentMockMvc;

    private ModelBuilderDocument modelBuilderDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ModelBuilderDocumentResource modelBuilderDocumentResource = new ModelBuilderDocumentResource(modelBuilderDocumentRepository, modelBuilderDocumentSearchRepository);
        this.restModelBuilderDocumentMockMvc = MockMvcBuilders.standaloneSetup(modelBuilderDocumentResource)
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
    public static ModelBuilderDocument createEntity(EntityManager em) {
        ModelBuilderDocument modelBuilderDocument = new ModelBuilderDocument()
                .reporter(DEFAULT_REPORTER)
                .auditor(DEFAULT_AUDITOR);
        return modelBuilderDocument;
    }

    @Before
    public void initTest() {
        modelBuilderDocumentSearchRepository.deleteAll();
        modelBuilderDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelBuilderDocument() throws Exception {
        int databaseSizeBeforeCreate = modelBuilderDocumentRepository.findAll().size();

        // Create the ModelBuilderDocument

        restModelBuilderDocumentMockMvc.perform(post("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelBuilderDocument)))
            .andExpect(status().isCreated());

        // Validate the ModelBuilderDocument in the database
        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ModelBuilderDocument testModelBuilderDocument = modelBuilderDocumentList.get(modelBuilderDocumentList.size() - 1);
        assertThat(testModelBuilderDocument.getReporter()).isEqualTo(DEFAULT_REPORTER);
        assertThat(testModelBuilderDocument.getAuditor()).isEqualTo(DEFAULT_AUDITOR);

        // Validate the ModelBuilderDocument in Elasticsearch
        ModelBuilderDocument modelBuilderDocumentEs = modelBuilderDocumentSearchRepository.findOne(testModelBuilderDocument.getId());
        assertThat(modelBuilderDocumentEs).isEqualToComparingFieldByField(testModelBuilderDocument);
    }

    @Test
    @Transactional
    public void createModelBuilderDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelBuilderDocumentRepository.findAll().size();

        // Create the ModelBuilderDocument with an existing ID
        ModelBuilderDocument existingModelBuilderDocument = new ModelBuilderDocument();
        existingModelBuilderDocument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelBuilderDocumentMockMvc.perform(post("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModelBuilderDocument)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReporterIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelBuilderDocumentRepository.findAll().size();
        // set the field null
        modelBuilderDocument.setReporter(null);

        // Create the ModelBuilderDocument, which fails.

        restModelBuilderDocumentMockMvc.perform(post("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelBuilderDocument)))
            .andExpect(status().isBadRequest());

        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuditorIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelBuilderDocumentRepository.findAll().size();
        // set the field null
        modelBuilderDocument.setAuditor(null);

        // Create the ModelBuilderDocument, which fails.

        restModelBuilderDocumentMockMvc.perform(post("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelBuilderDocument)))
            .andExpect(status().isBadRequest());

        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelBuilderDocuments() throws Exception {
        // Initialize the database
        modelBuilderDocumentRepository.saveAndFlush(modelBuilderDocument);

        // Get all the modelBuilderDocumentList
        restModelBuilderDocumentMockMvc.perform(get("/api/model-builder-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelBuilderDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].reporter").value(hasItem(DEFAULT_REPORTER.toString())))
            .andExpect(jsonPath("$.[*].auditor").value(hasItem(DEFAULT_AUDITOR.toString())));
    }

    @Test
    @Transactional
    public void getModelBuilderDocument() throws Exception {
        // Initialize the database
        modelBuilderDocumentRepository.saveAndFlush(modelBuilderDocument);

        // Get the modelBuilderDocument
        restModelBuilderDocumentMockMvc.perform(get("/api/model-builder-documents/{id}", modelBuilderDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelBuilderDocument.getId().intValue()))
            .andExpect(jsonPath("$.reporter").value(DEFAULT_REPORTER.toString()))
            .andExpect(jsonPath("$.auditor").value(DEFAULT_AUDITOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModelBuilderDocument() throws Exception {
        // Get the modelBuilderDocument
        restModelBuilderDocumentMockMvc.perform(get("/api/model-builder-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelBuilderDocument() throws Exception {
        // Initialize the database
        modelBuilderDocumentRepository.saveAndFlush(modelBuilderDocument);
        modelBuilderDocumentSearchRepository.save(modelBuilderDocument);
        int databaseSizeBeforeUpdate = modelBuilderDocumentRepository.findAll().size();

        // Update the modelBuilderDocument
        ModelBuilderDocument updatedModelBuilderDocument = modelBuilderDocumentRepository.findOne(modelBuilderDocument.getId());
        updatedModelBuilderDocument
                .reporter(UPDATED_REPORTER)
                .auditor(UPDATED_AUDITOR);

        restModelBuilderDocumentMockMvc.perform(put("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelBuilderDocument)))
            .andExpect(status().isOk());

        // Validate the ModelBuilderDocument in the database
        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeUpdate);
        ModelBuilderDocument testModelBuilderDocument = modelBuilderDocumentList.get(modelBuilderDocumentList.size() - 1);
        assertThat(testModelBuilderDocument.getReporter()).isEqualTo(UPDATED_REPORTER);
        assertThat(testModelBuilderDocument.getAuditor()).isEqualTo(UPDATED_AUDITOR);

        // Validate the ModelBuilderDocument in Elasticsearch
        ModelBuilderDocument modelBuilderDocumentEs = modelBuilderDocumentSearchRepository.findOne(testModelBuilderDocument.getId());
        assertThat(modelBuilderDocumentEs).isEqualToComparingFieldByField(testModelBuilderDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingModelBuilderDocument() throws Exception {
        int databaseSizeBeforeUpdate = modelBuilderDocumentRepository.findAll().size();

        // Create the ModelBuilderDocument

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelBuilderDocumentMockMvc.perform(put("/api/model-builder-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelBuilderDocument)))
            .andExpect(status().isCreated());

        // Validate the ModelBuilderDocument in the database
        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelBuilderDocument() throws Exception {
        // Initialize the database
        modelBuilderDocumentRepository.saveAndFlush(modelBuilderDocument);
        modelBuilderDocumentSearchRepository.save(modelBuilderDocument);
        int databaseSizeBeforeDelete = modelBuilderDocumentRepository.findAll().size();

        // Get the modelBuilderDocument
        restModelBuilderDocumentMockMvc.perform(delete("/api/model-builder-documents/{id}", modelBuilderDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modelBuilderDocumentExistsInEs = modelBuilderDocumentSearchRepository.exists(modelBuilderDocument.getId());
        assertThat(modelBuilderDocumentExistsInEs).isFalse();

        // Validate the database is empty
        List<ModelBuilderDocument> modelBuilderDocumentList = modelBuilderDocumentRepository.findAll();
        assertThat(modelBuilderDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModelBuilderDocument() throws Exception {
        // Initialize the database
        modelBuilderDocumentRepository.saveAndFlush(modelBuilderDocument);
        modelBuilderDocumentSearchRepository.save(modelBuilderDocument);

        // Search the modelBuilderDocument
        restModelBuilderDocumentMockMvc.perform(get("/api/_search/model-builder-documents?query=id:" + modelBuilderDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelBuilderDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].reporter").value(hasItem(DEFAULT_REPORTER.toString())))
            .andExpect(jsonPath("$.[*].auditor").value(hasItem(DEFAULT_AUDITOR.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelBuilderDocument.class);
    }
}
