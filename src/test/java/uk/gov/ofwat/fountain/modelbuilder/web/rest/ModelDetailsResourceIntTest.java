package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelDetails;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ModelDetailsSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static uk.gov.ofwat.fountain.modelbuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModelDetailsResource REST controller.
 *
 * @see ModelDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class ModelDetailsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_YEAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BASE_YEAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_YEAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_YEAR_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_DATA_CHANGES = false;
    private static final Boolean UPDATED_ALLOW_DATA_CHANGES = true;

    private static final String DEFAULT_MODEL_FAMILY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_FAMILY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MODEL_FAMILY_PARENT = false;
    private static final Boolean UPDATED_MODEL_FAMILY_PARENT = true;

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    private static final String DEFAULT_BRANCH_TAG = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RUN_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FOUNTAIN_MODEL_ID = 1;
    private static final Integer UPDATED_FOUNTAIN_MODEL_ID = 2;

    @Autowired
    private ModelDetailsRepository modelDetailsRepository;

    @Autowired
    private ModelDetailsSearchRepository modelDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModelDetailsMockMvc;

    private ModelDetails modelDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ModelDetailsResource modelDetailsResource = new ModelDetailsResource(modelDetailsRepository, modelDetailsSearchRepository);
        this.restModelDetailsMockMvc = MockMvcBuilders.standaloneSetup(modelDetailsResource)
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
    public static ModelDetails createEntity(EntityManager em) {
        ModelDetails modelDetails = new ModelDetails()
                .code(DEFAULT_CODE)
                .name(DEFAULT_NAME)
                .version(DEFAULT_VERSION)
                .modelType(DEFAULT_MODEL_TYPE)
                .textCode(DEFAULT_TEXT_CODE)
                .baseYearCode(DEFAULT_BASE_YEAR_CODE)
                .reportYearCode(DEFAULT_REPORT_YEAR_CODE)
                .allowDataChanges(DEFAULT_ALLOW_DATA_CHANGES)
                .modelFamilyCode(DEFAULT_MODEL_FAMILY_CODE)
                .modelFamilyParent(DEFAULT_MODEL_FAMILY_PARENT)
                .displayOrder(DEFAULT_DISPLAY_ORDER)
                .branchTag(DEFAULT_BRANCH_TAG)
                .runCode(DEFAULT_RUN_CODE)
                .lastModified(DEFAULT_LAST_MODIFIED)
                .created(DEFAULT_CREATED)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
                .fountainModelId(DEFAULT_FOUNTAIN_MODEL_ID);
        return modelDetails;
    }

    @Before
    public void initTest() {
        modelDetailsSearchRepository.deleteAll();
        modelDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelDetails() throws Exception {
        int databaseSizeBeforeCreate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isCreated());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ModelDetails testModelDetails = modelDetailsList.get(modelDetailsList.size() - 1);
        assertThat(testModelDetails.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModelDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModelDetails.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testModelDetails.getModelType()).isEqualTo(DEFAULT_MODEL_TYPE);
        assertThat(testModelDetails.getTextCode()).isEqualTo(DEFAULT_TEXT_CODE);
        assertThat(testModelDetails.getBaseYearCode()).isEqualTo(DEFAULT_BASE_YEAR_CODE);
        assertThat(testModelDetails.getReportYearCode()).isEqualTo(DEFAULT_REPORT_YEAR_CODE);
        assertThat(testModelDetails.isAllowDataChanges()).isEqualTo(DEFAULT_ALLOW_DATA_CHANGES);
        assertThat(testModelDetails.getModelFamilyCode()).isEqualTo(DEFAULT_MODEL_FAMILY_CODE);
        assertThat(testModelDetails.isModelFamilyParent()).isEqualTo(DEFAULT_MODEL_FAMILY_PARENT);
        assertThat(testModelDetails.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testModelDetails.getBranchTag()).isEqualTo(DEFAULT_BRANCH_TAG);
        assertThat(testModelDetails.getRunCode()).isEqualTo(DEFAULT_RUN_CODE);
        assertThat(testModelDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testModelDetails.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testModelDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testModelDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testModelDetails.getFountainModelId()).isEqualTo(DEFAULT_FOUNTAIN_MODEL_ID);

        // Validate the ModelDetails in Elasticsearch
        ModelDetails modelDetailsEs = modelDetailsSearchRepository.findOne(testModelDetails.getId());
        assertThat(modelDetailsEs).isEqualToComparingFieldByField(testModelDetails);
    }

    @Test
    @Transactional
    public void createModelDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails with an existing ID
        ModelDetails existingModelDetails = new ModelDetails();
        existingModelDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModelDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelDetailsRepository.findAll().size();
        // set the field null
        modelDetails.setCode(null);

        // Create the ModelDetails, which fails.

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isBadRequest());

        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelDetailsRepository.findAll().size();
        // set the field null
        modelDetails.setName(null);

        // Create the ModelDetails, which fails.

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isBadRequest());

        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelDetailsRepository.findAll().size();
        // set the field null
        modelDetails.setVersion(null);

        // Create the ModelDetails, which fails.

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isBadRequest());

        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelDetailsRepository.findAll().size();
        // set the field null
        modelDetails.setModelType(null);

        // Create the ModelDetails, which fails.

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isBadRequest());

        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelFamilyParentIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelDetailsRepository.findAll().size();
        // set the field null
        modelDetails.setModelFamilyParent(null);

        // Create the ModelDetails, which fails.

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isBadRequest());

        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);

        // Get all the modelDetailsList
        restModelDetailsMockMvc.perform(get("/api/model-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].modelType").value(hasItem(DEFAULT_MODEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].textCode").value(hasItem(DEFAULT_TEXT_CODE.toString())))
            .andExpect(jsonPath("$.[*].baseYearCode").value(hasItem(DEFAULT_BASE_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].reportYearCode").value(hasItem(DEFAULT_REPORT_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].allowDataChanges").value(hasItem(DEFAULT_ALLOW_DATA_CHANGES.booleanValue())))
            .andExpect(jsonPath("$.[*].modelFamilyCode").value(hasItem(DEFAULT_MODEL_FAMILY_CODE.toString())))
            .andExpect(jsonPath("$.[*].modelFamilyParent").value(hasItem(DEFAULT_MODEL_FAMILY_PARENT.booleanValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].branchTag").value(hasItem(DEFAULT_BRANCH_TAG.toString())))
            .andExpect(jsonPath("$.[*].runCode").value(hasItem(DEFAULT_RUN_CODE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].fountainModelId").value(hasItem(DEFAULT_FOUNTAIN_MODEL_ID)));
    }

    @Test
    @Transactional
    public void getModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);

        // Get the modelDetails
        restModelDetailsMockMvc.perform(get("/api/model-details/{id}", modelDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelDetails.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.modelType").value(DEFAULT_MODEL_TYPE.toString()))
            .andExpect(jsonPath("$.textCode").value(DEFAULT_TEXT_CODE.toString()))
            .andExpect(jsonPath("$.baseYearCode").value(DEFAULT_BASE_YEAR_CODE.toString()))
            .andExpect(jsonPath("$.reportYearCode").value(DEFAULT_REPORT_YEAR_CODE.toString()))
            .andExpect(jsonPath("$.allowDataChanges").value(DEFAULT_ALLOW_DATA_CHANGES.booleanValue()))
            .andExpect(jsonPath("$.modelFamilyCode").value(DEFAULT_MODEL_FAMILY_CODE.toString()))
            .andExpect(jsonPath("$.modelFamilyParent").value(DEFAULT_MODEL_FAMILY_PARENT.booleanValue()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.branchTag").value(DEFAULT_BRANCH_TAG.toString()))
            .andExpect(jsonPath("$.runCode").value(DEFAULT_RUN_CODE.toString()))
            .andExpect(jsonPath("$.lastModified").value(sameInstant(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.fountainModelId").value(DEFAULT_FOUNTAIN_MODEL_ID));
    }

    @Test
    @Transactional
    public void getNonExistingModelDetails() throws Exception {
        // Get the modelDetails
        restModelDetailsMockMvc.perform(get("/api/model-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);
        modelDetailsSearchRepository.save(modelDetails);
        int databaseSizeBeforeUpdate = modelDetailsRepository.findAll().size();

        // Update the modelDetails
        ModelDetails updatedModelDetails = modelDetailsRepository.findOne(modelDetails.getId());
        updatedModelDetails
                .code(UPDATED_CODE)
                .name(UPDATED_NAME)
                .version(UPDATED_VERSION)
                .modelType(UPDATED_MODEL_TYPE)
                .textCode(UPDATED_TEXT_CODE)
                .baseYearCode(UPDATED_BASE_YEAR_CODE)
                .reportYearCode(UPDATED_REPORT_YEAR_CODE)
                .allowDataChanges(UPDATED_ALLOW_DATA_CHANGES)
                .modelFamilyCode(UPDATED_MODEL_FAMILY_CODE)
                .modelFamilyParent(UPDATED_MODEL_FAMILY_PARENT)
                .displayOrder(UPDATED_DISPLAY_ORDER)
                .branchTag(UPDATED_BRANCH_TAG)
                .runCode(UPDATED_RUN_CODE)
                .lastModified(UPDATED_LAST_MODIFIED)
                .created(UPDATED_CREATED)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
                .fountainModelId(UPDATED_FOUNTAIN_MODEL_ID);

        restModelDetailsMockMvc.perform(put("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelDetails)))
            .andExpect(status().isOk());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeUpdate);
        ModelDetails testModelDetails = modelDetailsList.get(modelDetailsList.size() - 1);
        assertThat(testModelDetails.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModelDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelDetails.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testModelDetails.getModelType()).isEqualTo(UPDATED_MODEL_TYPE);
        assertThat(testModelDetails.getTextCode()).isEqualTo(UPDATED_TEXT_CODE);
        assertThat(testModelDetails.getBaseYearCode()).isEqualTo(UPDATED_BASE_YEAR_CODE);
        assertThat(testModelDetails.getReportYearCode()).isEqualTo(UPDATED_REPORT_YEAR_CODE);
        assertThat(testModelDetails.isAllowDataChanges()).isEqualTo(UPDATED_ALLOW_DATA_CHANGES);
        assertThat(testModelDetails.getModelFamilyCode()).isEqualTo(UPDATED_MODEL_FAMILY_CODE);
        assertThat(testModelDetails.isModelFamilyParent()).isEqualTo(UPDATED_MODEL_FAMILY_PARENT);
        assertThat(testModelDetails.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testModelDetails.getBranchTag()).isEqualTo(UPDATED_BRANCH_TAG);
        assertThat(testModelDetails.getRunCode()).isEqualTo(UPDATED_RUN_CODE);
        assertThat(testModelDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testModelDetails.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testModelDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testModelDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testModelDetails.getFountainModelId()).isEqualTo(UPDATED_FOUNTAIN_MODEL_ID);

        // Validate the ModelDetails in Elasticsearch
        ModelDetails modelDetailsEs = modelDetailsSearchRepository.findOne(testModelDetails.getId());
        assertThat(modelDetailsEs).isEqualToComparingFieldByField(testModelDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingModelDetails() throws Exception {
        int databaseSizeBeforeUpdate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelDetailsMockMvc.perform(put("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isCreated());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);
        modelDetailsSearchRepository.save(modelDetails);
        int databaseSizeBeforeDelete = modelDetailsRepository.findAll().size();

        // Get the modelDetails
        restModelDetailsMockMvc.perform(delete("/api/model-details/{id}", modelDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modelDetailsExistsInEs = modelDetailsSearchRepository.exists(modelDetails.getId());
        assertThat(modelDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);
        modelDetailsSearchRepository.save(modelDetails);

        // Search the modelDetails
        restModelDetailsMockMvc.perform(get("/api/_search/model-details?query=id:" + modelDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].modelType").value(hasItem(DEFAULT_MODEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].textCode").value(hasItem(DEFAULT_TEXT_CODE.toString())))
            .andExpect(jsonPath("$.[*].baseYearCode").value(hasItem(DEFAULT_BASE_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].reportYearCode").value(hasItem(DEFAULT_REPORT_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].allowDataChanges").value(hasItem(DEFAULT_ALLOW_DATA_CHANGES.booleanValue())))
            .andExpect(jsonPath("$.[*].modelFamilyCode").value(hasItem(DEFAULT_MODEL_FAMILY_CODE.toString())))
            .andExpect(jsonPath("$.[*].modelFamilyParent").value(hasItem(DEFAULT_MODEL_FAMILY_PARENT.booleanValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].branchTag").value(hasItem(DEFAULT_BRANCH_TAG.toString())))
            .andExpect(jsonPath("$.[*].runCode").value(hasItem(DEFAULT_RUN_CODE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].fountainModelId").value(hasItem(DEFAULT_FOUNTAIN_MODEL_ID)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelDetails.class);
    }
}
