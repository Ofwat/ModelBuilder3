package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails;
import uk.gov.ofwat.fountain.modelbuilder.repository.SectionDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.SectionDetailsSearchRepository;
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
 * Test class for the SectionDetailsResource REST controller.
 *
 * @see SectionDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class SectionDetailsResourceIntTest {

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_LINE_NUMBER = false;
    private static final Boolean UPDATED_USE_LINE_NUMBER = true;

    private static final Boolean DEFAULT_USE_ITEM_CODE = false;
    private static final Boolean UPDATED_USE_ITEM_CODE = true;

    private static final Boolean DEFAULT_USE_ITEM_DESCRIPTION = false;
    private static final Boolean UPDATED_USE_ITEM_DESCRIPTION = true;

    private static final Boolean DEFAULT_USE_UNIT = false;
    private static final Boolean UPDATED_USE_UNIT = true;

    private static final Boolean DEFAULT_USE_YEAR_CODE = false;
    private static final Boolean UPDATED_USE_YEAR_CODE = true;

    private static final Boolean DEFAULT_USE_CONFIDENCE_GRADES = false;
    private static final Boolean UPDATED_USE_CONFIDENCE_GRADES = true;

    private static final Boolean DEFAULT_ALLOW_GROUP_SELECTION = false;
    private static final Boolean UPDATED_ALLOW_GROUP_SELECTION = true;

    private static final Boolean DEFAULT_ALLOW_DATA_CHANGES = false;
    private static final Boolean UPDATED_ALLOW_DATA_CHANGES = true;

    private static final String DEFAULT_SECTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ITEM_CODE_COLUMN = 1;
    private static final Integer UPDATED_ITEM_CODE_COLUMN = 2;

    @Autowired
    private SectionDetailsRepository sectionDetailsRepository;

    @Autowired
    private SectionDetailsSearchRepository sectionDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionDetailsMockMvc;

    private SectionDetails sectionDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SectionDetailsResource sectionDetailsResource = new SectionDetailsResource(sectionDetailsRepository, sectionDetailsSearchRepository);
        this.restSectionDetailsMockMvc = MockMvcBuilders.standaloneSetup(sectionDetailsResource)
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
    public static SectionDetails createEntity(EntityManager em) {
        SectionDetails sectionDetails = new SectionDetails()
                .display(DEFAULT_DISPLAY)
                .code(DEFAULT_CODE)
                .groupType(DEFAULT_GROUP_TYPE)
                .useLineNumber(DEFAULT_USE_LINE_NUMBER)
                .useItemCode(DEFAULT_USE_ITEM_CODE)
                .useItemDescription(DEFAULT_USE_ITEM_DESCRIPTION)
                .useUnit(DEFAULT_USE_UNIT)
                .useYearCode(DEFAULT_USE_YEAR_CODE)
                .useConfidenceGrades(DEFAULT_USE_CONFIDENCE_GRADES)
                .allowGroupSelection(DEFAULT_ALLOW_GROUP_SELECTION)
                .allowDataChanges(DEFAULT_ALLOW_DATA_CHANGES)
                .sectionType(DEFAULT_SECTION_TYPE)
                .itemCodeColumn(DEFAULT_ITEM_CODE_COLUMN);
        return sectionDetails;
    }

    @Before
    public void initTest() {
        sectionDetailsSearchRepository.deleteAll();
        sectionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSectionDetails() throws Exception {
        int databaseSizeBeforeCreate = sectionDetailsRepository.findAll().size();

        // Create the SectionDetails

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isCreated());

        // Validate the SectionDetails in the database
        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SectionDetails testSectionDetails = sectionDetailsList.get(sectionDetailsList.size() - 1);
        assertThat(testSectionDetails.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testSectionDetails.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSectionDetails.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testSectionDetails.isUseLineNumber()).isEqualTo(DEFAULT_USE_LINE_NUMBER);
        assertThat(testSectionDetails.isUseItemCode()).isEqualTo(DEFAULT_USE_ITEM_CODE);
        assertThat(testSectionDetails.isUseItemDescription()).isEqualTo(DEFAULT_USE_ITEM_DESCRIPTION);
        assertThat(testSectionDetails.isUseUnit()).isEqualTo(DEFAULT_USE_UNIT);
        assertThat(testSectionDetails.isUseYearCode()).isEqualTo(DEFAULT_USE_YEAR_CODE);
        assertThat(testSectionDetails.isUseConfidenceGrades()).isEqualTo(DEFAULT_USE_CONFIDENCE_GRADES);
        assertThat(testSectionDetails.isAllowGroupSelection()).isEqualTo(DEFAULT_ALLOW_GROUP_SELECTION);
        assertThat(testSectionDetails.isAllowDataChanges()).isEqualTo(DEFAULT_ALLOW_DATA_CHANGES);
        assertThat(testSectionDetails.getSectionType()).isEqualTo(DEFAULT_SECTION_TYPE);
        assertThat(testSectionDetails.getItemCodeColumn()).isEqualTo(DEFAULT_ITEM_CODE_COLUMN);

        // Validate the SectionDetails in Elasticsearch
        SectionDetails sectionDetailsEs = sectionDetailsSearchRepository.findOne(testSectionDetails.getId());
        assertThat(sectionDetailsEs).isEqualToComparingFieldByField(testSectionDetails);
    }

    @Test
    @Transactional
    public void createSectionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionDetailsRepository.findAll().size();

        // Create the SectionDetails with an existing ID
        SectionDetails existingSectionDetails = new SectionDetails();
        existingSectionDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSectionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDisplayIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setDisplay(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setCode(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseLineNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseLineNumber(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseItemCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseItemCode(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseItemDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseItemDescription(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseUnit(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseYearCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseYearCode(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseConfidenceGradesIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionDetailsRepository.findAll().size();
        // set the field null
        sectionDetails.setUseConfidenceGrades(null);

        // Create the SectionDetails, which fails.

        restSectionDetailsMockMvc.perform(post("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isBadRequest());

        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSectionDetails() throws Exception {
        // Initialize the database
        sectionDetailsRepository.saveAndFlush(sectionDetails);

        // Get all the sectionDetailsList
        restSectionDetailsMockMvc.perform(get("/api/section-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].useLineNumber").value(hasItem(DEFAULT_USE_LINE_NUMBER.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemCode").value(hasItem(DEFAULT_USE_ITEM_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemDescription").value(hasItem(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].useUnit").value(hasItem(DEFAULT_USE_UNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].useYearCode").value(hasItem(DEFAULT_USE_YEAR_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useConfidenceGrades").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue())))
            .andExpect(jsonPath("$.[*].allowGroupSelection").value(hasItem(DEFAULT_ALLOW_GROUP_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].allowDataChanges").value(hasItem(DEFAULT_ALLOW_DATA_CHANGES.booleanValue())))
            .andExpect(jsonPath("$.[*].sectionType").value(hasItem(DEFAULT_SECTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].itemCodeColumn").value(hasItem(DEFAULT_ITEM_CODE_COLUMN)));
    }

    @Test
    @Transactional
    public void getSectionDetails() throws Exception {
        // Initialize the database
        sectionDetailsRepository.saveAndFlush(sectionDetails);

        // Get the sectionDetails
        restSectionDetailsMockMvc.perform(get("/api/section-details/{id}", sectionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sectionDetails.getId().intValue()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.useLineNumber").value(DEFAULT_USE_LINE_NUMBER.booleanValue()))
            .andExpect(jsonPath("$.useItemCode").value(DEFAULT_USE_ITEM_CODE.booleanValue()))
            .andExpect(jsonPath("$.useItemDescription").value(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.useUnit").value(DEFAULT_USE_UNIT.booleanValue()))
            .andExpect(jsonPath("$.useYearCode").value(DEFAULT_USE_YEAR_CODE.booleanValue()))
            .andExpect(jsonPath("$.useConfidenceGrades").value(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue()))
            .andExpect(jsonPath("$.allowGroupSelection").value(DEFAULT_ALLOW_GROUP_SELECTION.booleanValue()))
            .andExpect(jsonPath("$.allowDataChanges").value(DEFAULT_ALLOW_DATA_CHANGES.booleanValue()))
            .andExpect(jsonPath("$.sectionType").value(DEFAULT_SECTION_TYPE.toString()))
            .andExpect(jsonPath("$.itemCodeColumn").value(DEFAULT_ITEM_CODE_COLUMN));
    }

    @Test
    @Transactional
    public void getNonExistingSectionDetails() throws Exception {
        // Get the sectionDetails
        restSectionDetailsMockMvc.perform(get("/api/section-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSectionDetails() throws Exception {
        // Initialize the database
        sectionDetailsRepository.saveAndFlush(sectionDetails);
        sectionDetailsSearchRepository.save(sectionDetails);
        int databaseSizeBeforeUpdate = sectionDetailsRepository.findAll().size();

        // Update the sectionDetails
        SectionDetails updatedSectionDetails = sectionDetailsRepository.findOne(sectionDetails.getId());
        updatedSectionDetails
                .display(UPDATED_DISPLAY)
                .code(UPDATED_CODE)
                .groupType(UPDATED_GROUP_TYPE)
                .useLineNumber(UPDATED_USE_LINE_NUMBER)
                .useItemCode(UPDATED_USE_ITEM_CODE)
                .useItemDescription(UPDATED_USE_ITEM_DESCRIPTION)
                .useUnit(UPDATED_USE_UNIT)
                .useYearCode(UPDATED_USE_YEAR_CODE)
                .useConfidenceGrades(UPDATED_USE_CONFIDENCE_GRADES)
                .allowGroupSelection(UPDATED_ALLOW_GROUP_SELECTION)
                .allowDataChanges(UPDATED_ALLOW_DATA_CHANGES)
                .sectionType(UPDATED_SECTION_TYPE)
                .itemCodeColumn(UPDATED_ITEM_CODE_COLUMN);

        restSectionDetailsMockMvc.perform(put("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSectionDetails)))
            .andExpect(status().isOk());

        // Validate the SectionDetails in the database
        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeUpdate);
        SectionDetails testSectionDetails = sectionDetailsList.get(sectionDetailsList.size() - 1);
        assertThat(testSectionDetails.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testSectionDetails.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSectionDetails.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testSectionDetails.isUseLineNumber()).isEqualTo(UPDATED_USE_LINE_NUMBER);
        assertThat(testSectionDetails.isUseItemCode()).isEqualTo(UPDATED_USE_ITEM_CODE);
        assertThat(testSectionDetails.isUseItemDescription()).isEqualTo(UPDATED_USE_ITEM_DESCRIPTION);
        assertThat(testSectionDetails.isUseUnit()).isEqualTo(UPDATED_USE_UNIT);
        assertThat(testSectionDetails.isUseYearCode()).isEqualTo(UPDATED_USE_YEAR_CODE);
        assertThat(testSectionDetails.isUseConfidenceGrades()).isEqualTo(UPDATED_USE_CONFIDENCE_GRADES);
        assertThat(testSectionDetails.isAllowGroupSelection()).isEqualTo(UPDATED_ALLOW_GROUP_SELECTION);
        assertThat(testSectionDetails.isAllowDataChanges()).isEqualTo(UPDATED_ALLOW_DATA_CHANGES);
        assertThat(testSectionDetails.getSectionType()).isEqualTo(UPDATED_SECTION_TYPE);
        assertThat(testSectionDetails.getItemCodeColumn()).isEqualTo(UPDATED_ITEM_CODE_COLUMN);

        // Validate the SectionDetails in Elasticsearch
        SectionDetails sectionDetailsEs = sectionDetailsSearchRepository.findOne(testSectionDetails.getId());
        assertThat(sectionDetailsEs).isEqualToComparingFieldByField(testSectionDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingSectionDetails() throws Exception {
        int databaseSizeBeforeUpdate = sectionDetailsRepository.findAll().size();

        // Create the SectionDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionDetailsMockMvc.perform(put("/api/section-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDetails)))
            .andExpect(status().isCreated());

        // Validate the SectionDetails in the database
        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSectionDetails() throws Exception {
        // Initialize the database
        sectionDetailsRepository.saveAndFlush(sectionDetails);
        sectionDetailsSearchRepository.save(sectionDetails);
        int databaseSizeBeforeDelete = sectionDetailsRepository.findAll().size();

        // Get the sectionDetails
        restSectionDetailsMockMvc.perform(delete("/api/section-details/{id}", sectionDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sectionDetailsExistsInEs = sectionDetailsSearchRepository.exists(sectionDetails.getId());
        assertThat(sectionDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<SectionDetails> sectionDetailsList = sectionDetailsRepository.findAll();
        assertThat(sectionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSectionDetails() throws Exception {
        // Initialize the database
        sectionDetailsRepository.saveAndFlush(sectionDetails);
        sectionDetailsSearchRepository.save(sectionDetails);

        // Search the sectionDetails
        restSectionDetailsMockMvc.perform(get("/api/_search/section-details?query=id:" + sectionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].useLineNumber").value(hasItem(DEFAULT_USE_LINE_NUMBER.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemCode").value(hasItem(DEFAULT_USE_ITEM_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemDescription").value(hasItem(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].useUnit").value(hasItem(DEFAULT_USE_UNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].useYearCode").value(hasItem(DEFAULT_USE_YEAR_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useConfidenceGrades").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue())))
            .andExpect(jsonPath("$.[*].allowGroupSelection").value(hasItem(DEFAULT_ALLOW_GROUP_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].allowDataChanges").value(hasItem(DEFAULT_ALLOW_DATA_CHANGES.booleanValue())))
            .andExpect(jsonPath("$.[*].sectionType").value(hasItem(DEFAULT_SECTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].itemCodeColumn").value(hasItem(DEFAULT_ITEM_CODE_COLUMN)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionDetails.class);
    }
}
