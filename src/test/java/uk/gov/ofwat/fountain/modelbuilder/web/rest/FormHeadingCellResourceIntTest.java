package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;
import uk.gov.ofwat.fountain.modelbuilder.repository.FormHeadingCellRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormHeadingCellSearchRepository;
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
 * Test class for the FormHeadingCellResource REST controller.
 *
 * @see FormHeadingCellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class FormHeadingCellResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

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

    private static final String DEFAULT_ROW = "AAAAAAAAAA";
    private static final String UPDATED_ROW = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_HEADING_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_FORM_HEADING_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_ROW_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_ROW_SPAN = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_HEADING_COLUMN_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_FORM_HEADING_COLUMN_SPAN = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_WIDTH = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_DESCRIPTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_DESCRIPTION_CODE = "BBBBBBBBBB";

    @Autowired
    private FormHeadingCellRepository formHeadingCellRepository;

    @Autowired
    private FormHeadingCellSearchRepository formHeadingCellSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormHeadingCellMockMvc;

    private FormHeadingCell formHeadingCell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FormHeadingCellResource formHeadingCellResource = new FormHeadingCellResource(formHeadingCellRepository, formHeadingCellSearchRepository);
        this.restFormHeadingCellMockMvc = MockMvcBuilders.standaloneSetup(formHeadingCellResource)
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
    public static FormHeadingCell createEntity(EntityManager em) {
        FormHeadingCell formHeadingCell = new FormHeadingCell()
                .text(DEFAULT_TEXT)
                .useLineNumber(DEFAULT_USE_LINE_NUMBER)
                .useItemCode(DEFAULT_USE_ITEM_CODE)
                .useItemDescription(DEFAULT_USE_ITEM_DESCRIPTION)
                .useUnit(DEFAULT_USE_UNIT)
                .useYearCode(DEFAULT_USE_YEAR_CODE)
                .useConfidenceGrades(DEFAULT_USE_CONFIDENCE_GRADES)
                .row(DEFAULT_ROW)
                .formHeadingColumn(DEFAULT_FORM_HEADING_COLUMN)
                .rowSpan(DEFAULT_ROW_SPAN)
                .formHeadingColumnSpan(DEFAULT_FORM_HEADING_COLUMN_SPAN)
                .itemCode(DEFAULT_ITEM_CODE)
                .yearCode(DEFAULT_YEAR_CODE)
                .width(DEFAULT_WIDTH)
                .cellCode(DEFAULT_CELL_CODE)
                .groupDescriptionCode(DEFAULT_GROUP_DESCRIPTION_CODE);
        return formHeadingCell;
    }

    @Before
    public void initTest() {
        formHeadingCellSearchRepository.deleteAll();
        formHeadingCell = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormHeadingCell() throws Exception {
        int databaseSizeBeforeCreate = formHeadingCellRepository.findAll().size();

        // Create the FormHeadingCell

        restFormHeadingCellMockMvc.perform(post("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formHeadingCell)))
            .andExpect(status().isCreated());

        // Validate the FormHeadingCell in the database
        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeCreate + 1);
        FormHeadingCell testFormHeadingCell = formHeadingCellList.get(formHeadingCellList.size() - 1);
        assertThat(testFormHeadingCell.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testFormHeadingCell.isUseLineNumber()).isEqualTo(DEFAULT_USE_LINE_NUMBER);
        assertThat(testFormHeadingCell.isUseItemCode()).isEqualTo(DEFAULT_USE_ITEM_CODE);
        assertThat(testFormHeadingCell.isUseItemDescription()).isEqualTo(DEFAULT_USE_ITEM_DESCRIPTION);
        assertThat(testFormHeadingCell.isUseUnit()).isEqualTo(DEFAULT_USE_UNIT);
        assertThat(testFormHeadingCell.isUseYearCode()).isEqualTo(DEFAULT_USE_YEAR_CODE);
        assertThat(testFormHeadingCell.isUseConfidenceGrades()).isEqualTo(DEFAULT_USE_CONFIDENCE_GRADES);
        assertThat(testFormHeadingCell.getRow()).isEqualTo(DEFAULT_ROW);
        assertThat(testFormHeadingCell.getFormHeadingColumn()).isEqualTo(DEFAULT_FORM_HEADING_COLUMN);
        assertThat(testFormHeadingCell.getRowSpan()).isEqualTo(DEFAULT_ROW_SPAN);
        assertThat(testFormHeadingCell.getFormHeadingColumnSpan()).isEqualTo(DEFAULT_FORM_HEADING_COLUMN_SPAN);
        assertThat(testFormHeadingCell.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testFormHeadingCell.getYearCode()).isEqualTo(DEFAULT_YEAR_CODE);
        assertThat(testFormHeadingCell.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testFormHeadingCell.getCellCode()).isEqualTo(DEFAULT_CELL_CODE);
        assertThat(testFormHeadingCell.getGroupDescriptionCode()).isEqualTo(DEFAULT_GROUP_DESCRIPTION_CODE);

        // Validate the FormHeadingCell in Elasticsearch
        FormHeadingCell formHeadingCellEs = formHeadingCellSearchRepository.findOne(testFormHeadingCell.getId());
        assertThat(formHeadingCellEs).isEqualToComparingFieldByField(testFormHeadingCell);
    }

    @Test
    @Transactional
    public void createFormHeadingCellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formHeadingCellRepository.findAll().size();

        // Create the FormHeadingCell with an existing ID
        FormHeadingCell existingFormHeadingCell = new FormHeadingCell();
        existingFormHeadingCell.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormHeadingCellMockMvc.perform(post("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFormHeadingCell)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRowIsRequired() throws Exception {
        int databaseSizeBeforeTest = formHeadingCellRepository.findAll().size();
        // set the field null
        formHeadingCell.setRow(null);

        // Create the FormHeadingCell, which fails.

        restFormHeadingCellMockMvc.perform(post("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formHeadingCell)))
            .andExpect(status().isBadRequest());

        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormHeadingColumnIsRequired() throws Exception {
        int databaseSizeBeforeTest = formHeadingCellRepository.findAll().size();
        // set the field null
        formHeadingCell.setFormHeadingColumn(null);

        // Create the FormHeadingCell, which fails.

        restFormHeadingCellMockMvc.perform(post("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formHeadingCell)))
            .andExpect(status().isBadRequest());

        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormHeadingCells() throws Exception {
        // Initialize the database
        formHeadingCellRepository.saveAndFlush(formHeadingCell);

        // Get all the formHeadingCellList
        restFormHeadingCellMockMvc.perform(get("/api/form-heading-cells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formHeadingCell.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].useLineNumber").value(hasItem(DEFAULT_USE_LINE_NUMBER.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemCode").value(hasItem(DEFAULT_USE_ITEM_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemDescription").value(hasItem(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].useUnit").value(hasItem(DEFAULT_USE_UNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].useYearCode").value(hasItem(DEFAULT_USE_YEAR_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useConfidenceGrades").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue())))
            .andExpect(jsonPath("$.[*].row").value(hasItem(DEFAULT_ROW.toString())))
            .andExpect(jsonPath("$.[*].formHeadingColumn").value(hasItem(DEFAULT_FORM_HEADING_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].rowSpan").value(hasItem(DEFAULT_ROW_SPAN.toString())))
            .andExpect(jsonPath("$.[*].formHeadingColumnSpan").value(hasItem(DEFAULT_FORM_HEADING_COLUMN_SPAN.toString())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].yearCode").value(hasItem(DEFAULT_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())))
            .andExpect(jsonPath("$.[*].cellCode").value(hasItem(DEFAULT_CELL_CODE.toString())))
            .andExpect(jsonPath("$.[*].groupDescriptionCode").value(hasItem(DEFAULT_GROUP_DESCRIPTION_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFormHeadingCell() throws Exception {
        // Initialize the database
        formHeadingCellRepository.saveAndFlush(formHeadingCell);

        // Get the formHeadingCell
        restFormHeadingCellMockMvc.perform(get("/api/form-heading-cells/{id}", formHeadingCell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formHeadingCell.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.useLineNumber").value(DEFAULT_USE_LINE_NUMBER.booleanValue()))
            .andExpect(jsonPath("$.useItemCode").value(DEFAULT_USE_ITEM_CODE.booleanValue()))
            .andExpect(jsonPath("$.useItemDescription").value(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.useUnit").value(DEFAULT_USE_UNIT.booleanValue()))
            .andExpect(jsonPath("$.useYearCode").value(DEFAULT_USE_YEAR_CODE.booleanValue()))
            .andExpect(jsonPath("$.useConfidenceGrades").value(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue()))
            .andExpect(jsonPath("$.row").value(DEFAULT_ROW.toString()))
            .andExpect(jsonPath("$.formHeadingColumn").value(DEFAULT_FORM_HEADING_COLUMN.toString()))
            .andExpect(jsonPath("$.rowSpan").value(DEFAULT_ROW_SPAN.toString()))
            .andExpect(jsonPath("$.formHeadingColumnSpan").value(DEFAULT_FORM_HEADING_COLUMN_SPAN.toString()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.yearCode").value(DEFAULT_YEAR_CODE.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()))
            .andExpect(jsonPath("$.cellCode").value(DEFAULT_CELL_CODE.toString()))
            .andExpect(jsonPath("$.groupDescriptionCode").value(DEFAULT_GROUP_DESCRIPTION_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormHeadingCell() throws Exception {
        // Get the formHeadingCell
        restFormHeadingCellMockMvc.perform(get("/api/form-heading-cells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormHeadingCell() throws Exception {
        // Initialize the database
        formHeadingCellRepository.saveAndFlush(formHeadingCell);
        formHeadingCellSearchRepository.save(formHeadingCell);
        int databaseSizeBeforeUpdate = formHeadingCellRepository.findAll().size();

        // Update the formHeadingCell
        FormHeadingCell updatedFormHeadingCell = formHeadingCellRepository.findOne(formHeadingCell.getId());
        updatedFormHeadingCell
                .text(UPDATED_TEXT)
                .useLineNumber(UPDATED_USE_LINE_NUMBER)
                .useItemCode(UPDATED_USE_ITEM_CODE)
                .useItemDescription(UPDATED_USE_ITEM_DESCRIPTION)
                .useUnit(UPDATED_USE_UNIT)
                .useYearCode(UPDATED_USE_YEAR_CODE)
                .useConfidenceGrades(UPDATED_USE_CONFIDENCE_GRADES)
                .row(UPDATED_ROW)
                .formHeadingColumn(UPDATED_FORM_HEADING_COLUMN)
                .rowSpan(UPDATED_ROW_SPAN)
                .formHeadingColumnSpan(UPDATED_FORM_HEADING_COLUMN_SPAN)
                .itemCode(UPDATED_ITEM_CODE)
                .yearCode(UPDATED_YEAR_CODE)
                .width(UPDATED_WIDTH)
                .cellCode(UPDATED_CELL_CODE)
                .groupDescriptionCode(UPDATED_GROUP_DESCRIPTION_CODE);

        restFormHeadingCellMockMvc.perform(put("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormHeadingCell)))
            .andExpect(status().isOk());

        // Validate the FormHeadingCell in the database
        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeUpdate);
        FormHeadingCell testFormHeadingCell = formHeadingCellList.get(formHeadingCellList.size() - 1);
        assertThat(testFormHeadingCell.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testFormHeadingCell.isUseLineNumber()).isEqualTo(UPDATED_USE_LINE_NUMBER);
        assertThat(testFormHeadingCell.isUseItemCode()).isEqualTo(UPDATED_USE_ITEM_CODE);
        assertThat(testFormHeadingCell.isUseItemDescription()).isEqualTo(UPDATED_USE_ITEM_DESCRIPTION);
        assertThat(testFormHeadingCell.isUseUnit()).isEqualTo(UPDATED_USE_UNIT);
        assertThat(testFormHeadingCell.isUseYearCode()).isEqualTo(UPDATED_USE_YEAR_CODE);
        assertThat(testFormHeadingCell.isUseConfidenceGrades()).isEqualTo(UPDATED_USE_CONFIDENCE_GRADES);
        assertThat(testFormHeadingCell.getRow()).isEqualTo(UPDATED_ROW);
        assertThat(testFormHeadingCell.getFormHeadingColumn()).isEqualTo(UPDATED_FORM_HEADING_COLUMN);
        assertThat(testFormHeadingCell.getRowSpan()).isEqualTo(UPDATED_ROW_SPAN);
        assertThat(testFormHeadingCell.getFormHeadingColumnSpan()).isEqualTo(UPDATED_FORM_HEADING_COLUMN_SPAN);
        assertThat(testFormHeadingCell.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testFormHeadingCell.getYearCode()).isEqualTo(UPDATED_YEAR_CODE);
        assertThat(testFormHeadingCell.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testFormHeadingCell.getCellCode()).isEqualTo(UPDATED_CELL_CODE);
        assertThat(testFormHeadingCell.getGroupDescriptionCode()).isEqualTo(UPDATED_GROUP_DESCRIPTION_CODE);

        // Validate the FormHeadingCell in Elasticsearch
        FormHeadingCell formHeadingCellEs = formHeadingCellSearchRepository.findOne(testFormHeadingCell.getId());
        assertThat(formHeadingCellEs).isEqualToComparingFieldByField(testFormHeadingCell);
    }

    @Test
    @Transactional
    public void updateNonExistingFormHeadingCell() throws Exception {
        int databaseSizeBeforeUpdate = formHeadingCellRepository.findAll().size();

        // Create the FormHeadingCell

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormHeadingCellMockMvc.perform(put("/api/form-heading-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formHeadingCell)))
            .andExpect(status().isCreated());

        // Validate the FormHeadingCell in the database
        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormHeadingCell() throws Exception {
        // Initialize the database
        formHeadingCellRepository.saveAndFlush(formHeadingCell);
        formHeadingCellSearchRepository.save(formHeadingCell);
        int databaseSizeBeforeDelete = formHeadingCellRepository.findAll().size();

        // Get the formHeadingCell
        restFormHeadingCellMockMvc.perform(delete("/api/form-heading-cells/{id}", formHeadingCell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formHeadingCellExistsInEs = formHeadingCellSearchRepository.exists(formHeadingCell.getId());
        assertThat(formHeadingCellExistsInEs).isFalse();

        // Validate the database is empty
        List<FormHeadingCell> formHeadingCellList = formHeadingCellRepository.findAll();
        assertThat(formHeadingCellList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormHeadingCell() throws Exception {
        // Initialize the database
        formHeadingCellRepository.saveAndFlush(formHeadingCell);
        formHeadingCellSearchRepository.save(formHeadingCell);

        // Search the formHeadingCell
        restFormHeadingCellMockMvc.perform(get("/api/_search/form-heading-cells?query=id:" + formHeadingCell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formHeadingCell.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].useLineNumber").value(hasItem(DEFAULT_USE_LINE_NUMBER.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemCode").value(hasItem(DEFAULT_USE_ITEM_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useItemDescription").value(hasItem(DEFAULT_USE_ITEM_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].useUnit").value(hasItem(DEFAULT_USE_UNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].useYearCode").value(hasItem(DEFAULT_USE_YEAR_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].useConfidenceGrades").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADES.booleanValue())))
            .andExpect(jsonPath("$.[*].row").value(hasItem(DEFAULT_ROW.toString())))
            .andExpect(jsonPath("$.[*].formHeadingColumn").value(hasItem(DEFAULT_FORM_HEADING_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].rowSpan").value(hasItem(DEFAULT_ROW_SPAN.toString())))
            .andExpect(jsonPath("$.[*].formHeadingColumnSpan").value(hasItem(DEFAULT_FORM_HEADING_COLUMN_SPAN.toString())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].yearCode").value(hasItem(DEFAULT_YEAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())))
            .andExpect(jsonPath("$.[*].cellCode").value(hasItem(DEFAULT_CELL_CODE.toString())))
            .andExpect(jsonPath("$.[*].groupDescriptionCode").value(hasItem(DEFAULT_GROUP_DESCRIPTION_CODE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormHeadingCell.class);
    }
}
