package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails;
import uk.gov.ofwat.fountain.modelbuilder.repository.LineDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.LineDetailsSearchRepository;
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
 * Test class for the LineDetailsResource REST controller.
 *
 * @see LineDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class LineDetailsResourceIntTest {

    private static final Boolean DEFAULT_HEADING = false;
    private static final Boolean UPDATED_HEADING = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EQUATION = "AAAAAAAAAA";
    private static final String UPDATED_EQUATION = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LINE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_RULE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LINE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CONFIDENCE_GRADE = false;
    private static final Boolean UPDATED_USE_CONFIDENCE_GRADE = true;

    private static final String DEFAULT_VALIDATION_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_DECIMAL_PLACES = 1;
    private static final Integer UPDATED_DECIMAL_PLACES = 2;

    @Autowired
    private LineDetailsRepository lineDetailsRepository;

    @Autowired
    private LineDetailsSearchRepository lineDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLineDetailsMockMvc;

    private LineDetails lineDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LineDetailsResource lineDetailsResource = new LineDetailsResource(lineDetailsRepository, lineDetailsSearchRepository);
        this.restLineDetailsMockMvc = MockMvcBuilders.standaloneSetup(lineDetailsResource)
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
    public static LineDetails createEntity(EntityManager em) {
        LineDetails lineDetails = new LineDetails()
                .heading(DEFAULT_HEADING)
                .code(DEFAULT_CODE)
                .description(DEFAULT_DESCRIPTION)
                .equation(DEFAULT_EQUATION)
                .lineNumber(DEFAULT_LINE_NUMBER)
                .ruleText(DEFAULT_RULE_TEXT)
                .lineType(DEFAULT_LINE_TYPE)
                .companyType(DEFAULT_COMPANY_TYPE)
                .useConfidenceGrade(DEFAULT_USE_CONFIDENCE_GRADE)
                .validationRuleCode(DEFAULT_VALIDATION_RULE_CODE)
                .textCode(DEFAULT_TEXT_CODE)
                .unit(DEFAULT_UNIT)
                .decimalPlaces(DEFAULT_DECIMAL_PLACES);
        return lineDetails;
    }

    @Before
    public void initTest() {
        lineDetailsSearchRepository.deleteAll();
        lineDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineDetails() throws Exception {
        int databaseSizeBeforeCreate = lineDetailsRepository.findAll().size();

        // Create the LineDetails

        restLineDetailsMockMvc.perform(post("/api/line-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineDetails)))
            .andExpect(status().isCreated());

        // Validate the LineDetails in the database
        List<LineDetails> lineDetailsList = lineDetailsRepository.findAll();
        assertThat(lineDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LineDetails testLineDetails = lineDetailsList.get(lineDetailsList.size() - 1);
        assertThat(testLineDetails.isHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testLineDetails.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLineDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLineDetails.getEquation()).isEqualTo(DEFAULT_EQUATION);
        assertThat(testLineDetails.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testLineDetails.getRuleText()).isEqualTo(DEFAULT_RULE_TEXT);
        assertThat(testLineDetails.getLineType()).isEqualTo(DEFAULT_LINE_TYPE);
        assertThat(testLineDetails.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testLineDetails.isUseConfidenceGrade()).isEqualTo(DEFAULT_USE_CONFIDENCE_GRADE);
        assertThat(testLineDetails.getValidationRuleCode()).isEqualTo(DEFAULT_VALIDATION_RULE_CODE);
        assertThat(testLineDetails.getTextCode()).isEqualTo(DEFAULT_TEXT_CODE);
        assertThat(testLineDetails.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testLineDetails.getDecimalPlaces()).isEqualTo(DEFAULT_DECIMAL_PLACES);

        // Validate the LineDetails in Elasticsearch
        LineDetails lineDetailsEs = lineDetailsSearchRepository.findOne(testLineDetails.getId());
        assertThat(lineDetailsEs).isEqualToComparingFieldByField(testLineDetails);
    }

    @Test
    @Transactional
    public void createLineDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineDetailsRepository.findAll().size();

        // Create the LineDetails with an existing ID
        LineDetails existingLineDetails = new LineDetails();
        existingLineDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineDetailsMockMvc.perform(post("/api/line-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLineDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LineDetails> lineDetailsList = lineDetailsRepository.findAll();
        assertThat(lineDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLineDetails() throws Exception {
        // Initialize the database
        lineDetailsRepository.saveAndFlush(lineDetails);

        // Get all the lineDetailsList
        restLineDetailsMockMvc.perform(get("/api/line-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].equation").value(hasItem(DEFAULT_EQUATION.toString())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].ruleText").value(hasItem(DEFAULT_RULE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].lineType").value(hasItem(DEFAULT_LINE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].useConfidenceGrade").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].validationRuleCode").value(hasItem(DEFAULT_VALIDATION_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].textCode").value(hasItem(DEFAULT_TEXT_CODE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].decimalPlaces").value(hasItem(DEFAULT_DECIMAL_PLACES)));
    }

    @Test
    @Transactional
    public void getLineDetails() throws Exception {
        // Initialize the database
        lineDetailsRepository.saveAndFlush(lineDetails);

        // Get the lineDetails
        restLineDetailsMockMvc.perform(get("/api/line-details/{id}", lineDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineDetails.getId().intValue()))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.equation").value(DEFAULT_EQUATION.toString()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER.toString()))
            .andExpect(jsonPath("$.ruleText").value(DEFAULT_RULE_TEXT.toString()))
            .andExpect(jsonPath("$.lineType").value(DEFAULT_LINE_TYPE.toString()))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()))
            .andExpect(jsonPath("$.useConfidenceGrade").value(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue()))
            .andExpect(jsonPath("$.validationRuleCode").value(DEFAULT_VALIDATION_RULE_CODE.toString()))
            .andExpect(jsonPath("$.textCode").value(DEFAULT_TEXT_CODE.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.decimalPlaces").value(DEFAULT_DECIMAL_PLACES));
    }

    @Test
    @Transactional
    public void getNonExistingLineDetails() throws Exception {
        // Get the lineDetails
        restLineDetailsMockMvc.perform(get("/api/line-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineDetails() throws Exception {
        // Initialize the database
        lineDetailsRepository.saveAndFlush(lineDetails);
        lineDetailsSearchRepository.save(lineDetails);
        int databaseSizeBeforeUpdate = lineDetailsRepository.findAll().size();

        // Update the lineDetails
        LineDetails updatedLineDetails = lineDetailsRepository.findOne(lineDetails.getId());
        updatedLineDetails
                .heading(UPDATED_HEADING)
                .code(UPDATED_CODE)
                .description(UPDATED_DESCRIPTION)
                .equation(UPDATED_EQUATION)
                .lineNumber(UPDATED_LINE_NUMBER)
                .ruleText(UPDATED_RULE_TEXT)
                .lineType(UPDATED_LINE_TYPE)
                .companyType(UPDATED_COMPANY_TYPE)
                .useConfidenceGrade(UPDATED_USE_CONFIDENCE_GRADE)
                .validationRuleCode(UPDATED_VALIDATION_RULE_CODE)
                .textCode(UPDATED_TEXT_CODE)
                .unit(UPDATED_UNIT)
                .decimalPlaces(UPDATED_DECIMAL_PLACES);

        restLineDetailsMockMvc.perform(put("/api/line-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineDetails)))
            .andExpect(status().isOk());

        // Validate the LineDetails in the database
        List<LineDetails> lineDetailsList = lineDetailsRepository.findAll();
        assertThat(lineDetailsList).hasSize(databaseSizeBeforeUpdate);
        LineDetails testLineDetails = lineDetailsList.get(lineDetailsList.size() - 1);
        assertThat(testLineDetails.isHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testLineDetails.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLineDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLineDetails.getEquation()).isEqualTo(UPDATED_EQUATION);
        assertThat(testLineDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testLineDetails.getRuleText()).isEqualTo(UPDATED_RULE_TEXT);
        assertThat(testLineDetails.getLineType()).isEqualTo(UPDATED_LINE_TYPE);
        assertThat(testLineDetails.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testLineDetails.isUseConfidenceGrade()).isEqualTo(UPDATED_USE_CONFIDENCE_GRADE);
        assertThat(testLineDetails.getValidationRuleCode()).isEqualTo(UPDATED_VALIDATION_RULE_CODE);
        assertThat(testLineDetails.getTextCode()).isEqualTo(UPDATED_TEXT_CODE);
        assertThat(testLineDetails.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testLineDetails.getDecimalPlaces()).isEqualTo(UPDATED_DECIMAL_PLACES);

        // Validate the LineDetails in Elasticsearch
        LineDetails lineDetailsEs = lineDetailsSearchRepository.findOne(testLineDetails.getId());
        assertThat(lineDetailsEs).isEqualToComparingFieldByField(testLineDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingLineDetails() throws Exception {
        int databaseSizeBeforeUpdate = lineDetailsRepository.findAll().size();

        // Create the LineDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLineDetailsMockMvc.perform(put("/api/line-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineDetails)))
            .andExpect(status().isCreated());

        // Validate the LineDetails in the database
        List<LineDetails> lineDetailsList = lineDetailsRepository.findAll();
        assertThat(lineDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLineDetails() throws Exception {
        // Initialize the database
        lineDetailsRepository.saveAndFlush(lineDetails);
        lineDetailsSearchRepository.save(lineDetails);
        int databaseSizeBeforeDelete = lineDetailsRepository.findAll().size();

        // Get the lineDetails
        restLineDetailsMockMvc.perform(delete("/api/line-details/{id}", lineDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean lineDetailsExistsInEs = lineDetailsSearchRepository.exists(lineDetails.getId());
        assertThat(lineDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<LineDetails> lineDetailsList = lineDetailsRepository.findAll();
        assertThat(lineDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLineDetails() throws Exception {
        // Initialize the database
        lineDetailsRepository.saveAndFlush(lineDetails);
        lineDetailsSearchRepository.save(lineDetails);

        // Search the lineDetails
        restLineDetailsMockMvc.perform(get("/api/_search/line-details?query=id:" + lineDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].equation").value(hasItem(DEFAULT_EQUATION.toString())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].ruleText").value(hasItem(DEFAULT_RULE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].lineType").value(hasItem(DEFAULT_LINE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].useConfidenceGrade").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].validationRuleCode").value(hasItem(DEFAULT_VALIDATION_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].textCode").value(hasItem(DEFAULT_TEXT_CODE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].decimalPlaces").value(hasItem(DEFAULT_DECIMAL_PLACES)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineDetails.class);
    }
}
