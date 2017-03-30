package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.Input;
import uk.gov.ofwat.fountain.modelbuilder.repository.InputRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.InputSearchRepository;
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
 * Test class for the InputResource REST controller.
 *
 * @see InputResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class InputResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_INPUT = false;
    private static final Boolean UPDATED_DEFAULT_INPUT = true;

    private static final String DEFAULT_MODEL_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private InputSearchRepository inputSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInputMockMvc;

    private Input input;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            InputResource inputResource = new InputResource(inputRepository, inputSearchRepository);
        this.restInputMockMvc = MockMvcBuilders.standaloneSetup(inputResource)
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
    public static Input createEntity(EntityManager em) {
        Input input = new Input()
                .code(DEFAULT_CODE)
                .tag(DEFAULT_TAG)
                .version(DEFAULT_VERSION)
                .company(DEFAULT_COMPANY)
                .defaultInput(DEFAULT_DEFAULT_INPUT)
                .modelReference(DEFAULT_MODEL_REFERENCE);
        return input;
    }

    @Before
    public void initTest() {
        inputSearchRepository.deleteAll();
        input = createEntity(em);
    }

    @Test
    @Transactional
    public void createInput() throws Exception {
        int databaseSizeBeforeCreate = inputRepository.findAll().size();

        // Create the Input

        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isCreated());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeCreate + 1);
        Input testInput = inputList.get(inputList.size() - 1);
        assertThat(testInput.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInput.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testInput.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testInput.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testInput.isDefaultInput()).isEqualTo(DEFAULT_DEFAULT_INPUT);
        assertThat(testInput.getModelReference()).isEqualTo(DEFAULT_MODEL_REFERENCE);

        // Validate the Input in Elasticsearch
        Input inputEs = inputSearchRepository.findOne(testInput.getId());
        assertThat(inputEs).isEqualToComparingFieldByField(testInput);
    }

    @Test
    @Transactional
    public void createInputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputRepository.findAll().size();

        // Create the Input with an existing ID
        Input existingInput = new Input();
        existingInput.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingInput)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputRepository.findAll().size();
        // set the field null
        input.setCode(null);

        // Create the Input, which fails.

        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isBadRequest());

        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputRepository.findAll().size();
        // set the field null
        input.setVersion(null);

        // Create the Input, which fails.

        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isBadRequest());

        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultInputIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputRepository.findAll().size();
        // set the field null
        input.setDefaultInput(null);

        // Create the Input, which fails.

        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isBadRequest());

        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputRepository.findAll().size();
        // set the field null
        input.setModelReference(null);

        // Create the Input, which fails.

        restInputMockMvc.perform(post("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isBadRequest());

        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInputs() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        // Get all the inputList
        restInputMockMvc.perform(get("/api/inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(input.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].defaultInput").value(hasItem(DEFAULT_DEFAULT_INPUT.booleanValue())))
            .andExpect(jsonPath("$.[*].modelReference").value(hasItem(DEFAULT_MODEL_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        // Get the input
        restInputMockMvc.perform(get("/api/inputs/{id}", input.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(input.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.defaultInput").value(DEFAULT_DEFAULT_INPUT.booleanValue()))
            .andExpect(jsonPath("$.modelReference").value(DEFAULT_MODEL_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInput() throws Exception {
        // Get the input
        restInputMockMvc.perform(get("/api/inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);
        inputSearchRepository.save(input);
        int databaseSizeBeforeUpdate = inputRepository.findAll().size();

        // Update the input
        Input updatedInput = inputRepository.findOne(input.getId());
        updatedInput
                .code(UPDATED_CODE)
                .tag(UPDATED_TAG)
                .version(UPDATED_VERSION)
                .company(UPDATED_COMPANY)
                .defaultInput(UPDATED_DEFAULT_INPUT)
                .modelReference(UPDATED_MODEL_REFERENCE);

        restInputMockMvc.perform(put("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInput)))
            .andExpect(status().isOk());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeUpdate);
        Input testInput = inputList.get(inputList.size() - 1);
        assertThat(testInput.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInput.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testInput.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testInput.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testInput.isDefaultInput()).isEqualTo(UPDATED_DEFAULT_INPUT);
        assertThat(testInput.getModelReference()).isEqualTo(UPDATED_MODEL_REFERENCE);

        // Validate the Input in Elasticsearch
        Input inputEs = inputSearchRepository.findOne(testInput.getId());
        assertThat(inputEs).isEqualToComparingFieldByField(testInput);
    }

    @Test
    @Transactional
    public void updateNonExistingInput() throws Exception {
        int databaseSizeBeforeUpdate = inputRepository.findAll().size();

        // Create the Input

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInputMockMvc.perform(put("/api/inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(input)))
            .andExpect(status().isCreated());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);
        inputSearchRepository.save(input);
        int databaseSizeBeforeDelete = inputRepository.findAll().size();

        // Get the input
        restInputMockMvc.perform(delete("/api/inputs/{id}", input.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean inputExistsInEs = inputSearchRepository.exists(input.getId());
        assertThat(inputExistsInEs).isFalse();

        // Validate the database is empty
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);
        inputSearchRepository.save(input);

        // Search the input
        restInputMockMvc.perform(get("/api/_search/inputs?query=id:" + input.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(input.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].defaultInput").value(hasItem(DEFAULT_DEFAULT_INPUT.booleanValue())))
            .andExpect(jsonPath("$.[*].modelReference").value(hasItem(DEFAULT_MODEL_REFERENCE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Input.class);
    }
}
