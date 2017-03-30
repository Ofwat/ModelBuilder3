package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleItem;
import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleItemRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.ValidationRuleItemSearchRepository;
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
 * Test class for the ValidationRuleItemResource REST controller.
 *
 * @see ValidationRuleItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class ValidationRuleItemResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATE = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ValidationRuleItemRepository validationRuleItemRepository;

    @Autowired
    private ValidationRuleItemSearchRepository validationRuleItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValidationRuleItemMockMvc;

    private ValidationRuleItem validationRuleItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ValidationRuleItemResource validationRuleItemResource = new ValidationRuleItemResource(validationRuleItemRepository, validationRuleItemSearchRepository);
        this.restValidationRuleItemMockMvc = MockMvcBuilders.standaloneSetup(validationRuleItemResource)
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
    public static ValidationRuleItem createEntity(EntityManager em) {
        ValidationRuleItem validationRuleItem = new ValidationRuleItem()
                .type(DEFAULT_TYPE)
                .evaluate(DEFAULT_EVALUATE)
                .value(DEFAULT_VALUE);
        return validationRuleItem;
    }

    @Before
    public void initTest() {
        validationRuleItemSearchRepository.deleteAll();
        validationRuleItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createValidationRuleItem() throws Exception {
        int databaseSizeBeforeCreate = validationRuleItemRepository.findAll().size();

        // Create the ValidationRuleItem

        restValidationRuleItemMockMvc.perform(post("/api/validation-rule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationRuleItem)))
            .andExpect(status().isCreated());

        // Validate the ValidationRuleItem in the database
        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeCreate + 1);
        ValidationRuleItem testValidationRuleItem = validationRuleItemList.get(validationRuleItemList.size() - 1);
        assertThat(testValidationRuleItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testValidationRuleItem.getEvaluate()).isEqualTo(DEFAULT_EVALUATE);
        assertThat(testValidationRuleItem.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the ValidationRuleItem in Elasticsearch
        ValidationRuleItem validationRuleItemEs = validationRuleItemSearchRepository.findOne(testValidationRuleItem.getId());
        assertThat(validationRuleItemEs).isEqualToComparingFieldByField(testValidationRuleItem);
    }

    @Test
    @Transactional
    public void createValidationRuleItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationRuleItemRepository.findAll().size();

        // Create the ValidationRuleItem with an existing ID
        ValidationRuleItem existingValidationRuleItem = new ValidationRuleItem();
        existingValidationRuleItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationRuleItemMockMvc.perform(post("/api/validation-rule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingValidationRuleItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationRuleItemRepository.findAll().size();
        // set the field null
        validationRuleItem.setType(null);

        // Create the ValidationRuleItem, which fails.

        restValidationRuleItemMockMvc.perform(post("/api/validation-rule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationRuleItem)))
            .andExpect(status().isBadRequest());

        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValidationRuleItems() throws Exception {
        // Initialize the database
        validationRuleItemRepository.saveAndFlush(validationRuleItem);

        // Get all the validationRuleItemList
        restValidationRuleItemMockMvc.perform(get("/api/validation-rule-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationRuleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].evaluate").value(hasItem(DEFAULT_EVALUATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getValidationRuleItem() throws Exception {
        // Initialize the database
        validationRuleItemRepository.saveAndFlush(validationRuleItem);

        // Get the validationRuleItem
        restValidationRuleItemMockMvc.perform(get("/api/validation-rule-items/{id}", validationRuleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(validationRuleItem.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.evaluate").value(DEFAULT_EVALUATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValidationRuleItem() throws Exception {
        // Get the validationRuleItem
        restValidationRuleItemMockMvc.perform(get("/api/validation-rule-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValidationRuleItem() throws Exception {
        // Initialize the database
        validationRuleItemRepository.saveAndFlush(validationRuleItem);
        validationRuleItemSearchRepository.save(validationRuleItem);
        int databaseSizeBeforeUpdate = validationRuleItemRepository.findAll().size();

        // Update the validationRuleItem
        ValidationRuleItem updatedValidationRuleItem = validationRuleItemRepository.findOne(validationRuleItem.getId());
        updatedValidationRuleItem
                .type(UPDATED_TYPE)
                .evaluate(UPDATED_EVALUATE)
                .value(UPDATED_VALUE);

        restValidationRuleItemMockMvc.perform(put("/api/validation-rule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValidationRuleItem)))
            .andExpect(status().isOk());

        // Validate the ValidationRuleItem in the database
        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeUpdate);
        ValidationRuleItem testValidationRuleItem = validationRuleItemList.get(validationRuleItemList.size() - 1);
        assertThat(testValidationRuleItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testValidationRuleItem.getEvaluate()).isEqualTo(UPDATED_EVALUATE);
        assertThat(testValidationRuleItem.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the ValidationRuleItem in Elasticsearch
        ValidationRuleItem validationRuleItemEs = validationRuleItemSearchRepository.findOne(testValidationRuleItem.getId());
        assertThat(validationRuleItemEs).isEqualToComparingFieldByField(testValidationRuleItem);
    }

    @Test
    @Transactional
    public void updateNonExistingValidationRuleItem() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleItemRepository.findAll().size();

        // Create the ValidationRuleItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValidationRuleItemMockMvc.perform(put("/api/validation-rule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationRuleItem)))
            .andExpect(status().isCreated());

        // Validate the ValidationRuleItem in the database
        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValidationRuleItem() throws Exception {
        // Initialize the database
        validationRuleItemRepository.saveAndFlush(validationRuleItem);
        validationRuleItemSearchRepository.save(validationRuleItem);
        int databaseSizeBeforeDelete = validationRuleItemRepository.findAll().size();

        // Get the validationRuleItem
        restValidationRuleItemMockMvc.perform(delete("/api/validation-rule-items/{id}", validationRuleItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean validationRuleItemExistsInEs = validationRuleItemSearchRepository.exists(validationRuleItem.getId());
        assertThat(validationRuleItemExistsInEs).isFalse();

        // Validate the database is empty
        List<ValidationRuleItem> validationRuleItemList = validationRuleItemRepository.findAll();
        assertThat(validationRuleItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchValidationRuleItem() throws Exception {
        // Initialize the database
        validationRuleItemRepository.saveAndFlush(validationRuleItem);
        validationRuleItemSearchRepository.save(validationRuleItem);

        // Search the validationRuleItem
        restValidationRuleItemMockMvc.perform(get("/api/_search/validation-rule-items?query=id:" + validationRuleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationRuleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].evaluate").value(hasItem(DEFAULT_EVALUATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationRuleItem.class);
    }
}
