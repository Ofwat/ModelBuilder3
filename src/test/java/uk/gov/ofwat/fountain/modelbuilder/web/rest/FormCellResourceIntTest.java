package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell;
import uk.gov.ofwat.fountain.modelbuilder.repository.FormCellRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.FormCellSearchRepository;
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
 * Test class for the FormCellResource REST controller.
 *
 * @see FormCellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class FormCellResourceIntTest {

    private static final String DEFAULT_CELL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CONFIDENCE_GRADE = false;
    private static final Boolean UPDATED_USE_CONFIDENCE_GRADE = true;

    private static final Boolean DEFAULT_INPUT_CONFIDENCE_GRADE = false;
    private static final Boolean UPDATED_INPUT_CONFIDENCE_GRADE = true;

    private static final String DEFAULT_CONFIDENCE_GRADE_INPUT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIDENCE_GRADE_INPUT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ROW_HEADING_SOURCE = false;
    private static final Boolean UPDATED_ROW_HEADING_SOURCE = true;

    private static final Boolean DEFAULT_COLUMN_HEADING_SOURCE = false;
    private static final Boolean UPDATED_COLUMN_HEADING_SOURCE = true;

    private static final String DEFAULT_GROUP_DESCRIPTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_DESCRIPTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROW = "AAAAAAAAAA";
    private static final String UPDATED_ROW = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_FORM_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_ROW_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_ROW_SPAN = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_COLUMN_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_FORM_COLUMN_SPAN = "BBBBBBBBBB";

    private static final String DEFAULT_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_WIDTH = "BBBBBBBBBB";

    @Autowired
    private FormCellRepository formCellRepository;

    @Autowired
    private FormCellSearchRepository formCellSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormCellMockMvc;

    private FormCell formCell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FormCellResource formCellResource = new FormCellResource(formCellRepository, formCellSearchRepository);
        this.restFormCellMockMvc = MockMvcBuilders.standaloneSetup(formCellResource)
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
    public static FormCell createEntity(EntityManager em) {
        FormCell formCell = new FormCell()
                .cellCode(DEFAULT_CELL_CODE)
                .useConfidenceGrade(DEFAULT_USE_CONFIDENCE_GRADE)
                .inputConfidenceGrade(DEFAULT_INPUT_CONFIDENCE_GRADE)
                .confidenceGradeInputCode(DEFAULT_CONFIDENCE_GRADE_INPUT_CODE)
                .rowHeadingSource(DEFAULT_ROW_HEADING_SOURCE)
                .columnHeadingSource(DEFAULT_COLUMN_HEADING_SOURCE)
                .groupDescriptionCode(DEFAULT_GROUP_DESCRIPTION_CODE)
                .row(DEFAULT_ROW)
                .formColumn(DEFAULT_FORM_COLUMN)
                .rowSpan(DEFAULT_ROW_SPAN)
                .formColumnSpan(DEFAULT_FORM_COLUMN_SPAN)
                .width(DEFAULT_WIDTH);
        return formCell;
    }

    @Before
    public void initTest() {
        formCellSearchRepository.deleteAll();
        formCell = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormCell() throws Exception {
        int databaseSizeBeforeCreate = formCellRepository.findAll().size();

        // Create the FormCell

        restFormCellMockMvc.perform(post("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formCell)))
            .andExpect(status().isCreated());

        // Validate the FormCell in the database
        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeCreate + 1);
        FormCell testFormCell = formCellList.get(formCellList.size() - 1);
        assertThat(testFormCell.getCellCode()).isEqualTo(DEFAULT_CELL_CODE);
        assertThat(testFormCell.isUseConfidenceGrade()).isEqualTo(DEFAULT_USE_CONFIDENCE_GRADE);
        assertThat(testFormCell.isInputConfidenceGrade()).isEqualTo(DEFAULT_INPUT_CONFIDENCE_GRADE);
        assertThat(testFormCell.getConfidenceGradeInputCode()).isEqualTo(DEFAULT_CONFIDENCE_GRADE_INPUT_CODE);
        assertThat(testFormCell.isRowHeadingSource()).isEqualTo(DEFAULT_ROW_HEADING_SOURCE);
        assertThat(testFormCell.isColumnHeadingSource()).isEqualTo(DEFAULT_COLUMN_HEADING_SOURCE);
        assertThat(testFormCell.getGroupDescriptionCode()).isEqualTo(DEFAULT_GROUP_DESCRIPTION_CODE);
        assertThat(testFormCell.getRow()).isEqualTo(DEFAULT_ROW);
        assertThat(testFormCell.getFormColumn()).isEqualTo(DEFAULT_FORM_COLUMN);
        assertThat(testFormCell.getRowSpan()).isEqualTo(DEFAULT_ROW_SPAN);
        assertThat(testFormCell.getFormColumnSpan()).isEqualTo(DEFAULT_FORM_COLUMN_SPAN);
        assertThat(testFormCell.getWidth()).isEqualTo(DEFAULT_WIDTH);

        // Validate the FormCell in Elasticsearch
        FormCell formCellEs = formCellSearchRepository.findOne(testFormCell.getId());
        assertThat(formCellEs).isEqualToComparingFieldByField(testFormCell);
    }

    @Test
    @Transactional
    public void createFormCellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formCellRepository.findAll().size();

        // Create the FormCell with an existing ID
        FormCell existingFormCell = new FormCell();
        existingFormCell.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormCellMockMvc.perform(post("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFormCell)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRowIsRequired() throws Exception {
        int databaseSizeBeforeTest = formCellRepository.findAll().size();
        // set the field null
        formCell.setRow(null);

        // Create the FormCell, which fails.

        restFormCellMockMvc.perform(post("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formCell)))
            .andExpect(status().isBadRequest());

        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormColumnIsRequired() throws Exception {
        int databaseSizeBeforeTest = formCellRepository.findAll().size();
        // set the field null
        formCell.setFormColumn(null);

        // Create the FormCell, which fails.

        restFormCellMockMvc.perform(post("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formCell)))
            .andExpect(status().isBadRequest());

        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormCells() throws Exception {
        // Initialize the database
        formCellRepository.saveAndFlush(formCell);

        // Get all the formCellList
        restFormCellMockMvc.perform(get("/api/form-cells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formCell.getId().intValue())))
            .andExpect(jsonPath("$.[*].cellCode").value(hasItem(DEFAULT_CELL_CODE.toString())))
            .andExpect(jsonPath("$.[*].useConfidenceGrade").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].inputConfidenceGrade").value(hasItem(DEFAULT_INPUT_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].confidenceGradeInputCode").value(hasItem(DEFAULT_CONFIDENCE_GRADE_INPUT_CODE.toString())))
            .andExpect(jsonPath("$.[*].rowHeadingSource").value(hasItem(DEFAULT_ROW_HEADING_SOURCE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnHeadingSource").value(hasItem(DEFAULT_COLUMN_HEADING_SOURCE.booleanValue())))
            .andExpect(jsonPath("$.[*].groupDescriptionCode").value(hasItem(DEFAULT_GROUP_DESCRIPTION_CODE.toString())))
            .andExpect(jsonPath("$.[*].row").value(hasItem(DEFAULT_ROW.toString())))
            .andExpect(jsonPath("$.[*].formColumn").value(hasItem(DEFAULT_FORM_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].rowSpan").value(hasItem(DEFAULT_ROW_SPAN.toString())))
            .andExpect(jsonPath("$.[*].formColumnSpan").value(hasItem(DEFAULT_FORM_COLUMN_SPAN.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())));
    }

    @Test
    @Transactional
    public void getFormCell() throws Exception {
        // Initialize the database
        formCellRepository.saveAndFlush(formCell);

        // Get the formCell
        restFormCellMockMvc.perform(get("/api/form-cells/{id}", formCell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formCell.getId().intValue()))
            .andExpect(jsonPath("$.cellCode").value(DEFAULT_CELL_CODE.toString()))
            .andExpect(jsonPath("$.useConfidenceGrade").value(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue()))
            .andExpect(jsonPath("$.inputConfidenceGrade").value(DEFAULT_INPUT_CONFIDENCE_GRADE.booleanValue()))
            .andExpect(jsonPath("$.confidenceGradeInputCode").value(DEFAULT_CONFIDENCE_GRADE_INPUT_CODE.toString()))
            .andExpect(jsonPath("$.rowHeadingSource").value(DEFAULT_ROW_HEADING_SOURCE.booleanValue()))
            .andExpect(jsonPath("$.columnHeadingSource").value(DEFAULT_COLUMN_HEADING_SOURCE.booleanValue()))
            .andExpect(jsonPath("$.groupDescriptionCode").value(DEFAULT_GROUP_DESCRIPTION_CODE.toString()))
            .andExpect(jsonPath("$.row").value(DEFAULT_ROW.toString()))
            .andExpect(jsonPath("$.formColumn").value(DEFAULT_FORM_COLUMN.toString()))
            .andExpect(jsonPath("$.rowSpan").value(DEFAULT_ROW_SPAN.toString()))
            .andExpect(jsonPath("$.formColumnSpan").value(DEFAULT_FORM_COLUMN_SPAN.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormCell() throws Exception {
        // Get the formCell
        restFormCellMockMvc.perform(get("/api/form-cells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormCell() throws Exception {
        // Initialize the database
        formCellRepository.saveAndFlush(formCell);
        formCellSearchRepository.save(formCell);
        int databaseSizeBeforeUpdate = formCellRepository.findAll().size();

        // Update the formCell
        FormCell updatedFormCell = formCellRepository.findOne(formCell.getId());
        updatedFormCell
                .cellCode(UPDATED_CELL_CODE)
                .useConfidenceGrade(UPDATED_USE_CONFIDENCE_GRADE)
                .inputConfidenceGrade(UPDATED_INPUT_CONFIDENCE_GRADE)
                .confidenceGradeInputCode(UPDATED_CONFIDENCE_GRADE_INPUT_CODE)
                .rowHeadingSource(UPDATED_ROW_HEADING_SOURCE)
                .columnHeadingSource(UPDATED_COLUMN_HEADING_SOURCE)
                .groupDescriptionCode(UPDATED_GROUP_DESCRIPTION_CODE)
                .row(UPDATED_ROW)
                .formColumn(UPDATED_FORM_COLUMN)
                .rowSpan(UPDATED_ROW_SPAN)
                .formColumnSpan(UPDATED_FORM_COLUMN_SPAN)
                .width(UPDATED_WIDTH);

        restFormCellMockMvc.perform(put("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormCell)))
            .andExpect(status().isOk());

        // Validate the FormCell in the database
        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeUpdate);
        FormCell testFormCell = formCellList.get(formCellList.size() - 1);
        assertThat(testFormCell.getCellCode()).isEqualTo(UPDATED_CELL_CODE);
        assertThat(testFormCell.isUseConfidenceGrade()).isEqualTo(UPDATED_USE_CONFIDENCE_GRADE);
        assertThat(testFormCell.isInputConfidenceGrade()).isEqualTo(UPDATED_INPUT_CONFIDENCE_GRADE);
        assertThat(testFormCell.getConfidenceGradeInputCode()).isEqualTo(UPDATED_CONFIDENCE_GRADE_INPUT_CODE);
        assertThat(testFormCell.isRowHeadingSource()).isEqualTo(UPDATED_ROW_HEADING_SOURCE);
        assertThat(testFormCell.isColumnHeadingSource()).isEqualTo(UPDATED_COLUMN_HEADING_SOURCE);
        assertThat(testFormCell.getGroupDescriptionCode()).isEqualTo(UPDATED_GROUP_DESCRIPTION_CODE);
        assertThat(testFormCell.getRow()).isEqualTo(UPDATED_ROW);
        assertThat(testFormCell.getFormColumn()).isEqualTo(UPDATED_FORM_COLUMN);
        assertThat(testFormCell.getRowSpan()).isEqualTo(UPDATED_ROW_SPAN);
        assertThat(testFormCell.getFormColumnSpan()).isEqualTo(UPDATED_FORM_COLUMN_SPAN);
        assertThat(testFormCell.getWidth()).isEqualTo(UPDATED_WIDTH);

        // Validate the FormCell in Elasticsearch
        FormCell formCellEs = formCellSearchRepository.findOne(testFormCell.getId());
        assertThat(formCellEs).isEqualToComparingFieldByField(testFormCell);
    }

    @Test
    @Transactional
    public void updateNonExistingFormCell() throws Exception {
        int databaseSizeBeforeUpdate = formCellRepository.findAll().size();

        // Create the FormCell

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormCellMockMvc.perform(put("/api/form-cells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formCell)))
            .andExpect(status().isCreated());

        // Validate the FormCell in the database
        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormCell() throws Exception {
        // Initialize the database
        formCellRepository.saveAndFlush(formCell);
        formCellSearchRepository.save(formCell);
        int databaseSizeBeforeDelete = formCellRepository.findAll().size();

        // Get the formCell
        restFormCellMockMvc.perform(delete("/api/form-cells/{id}", formCell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formCellExistsInEs = formCellSearchRepository.exists(formCell.getId());
        assertThat(formCellExistsInEs).isFalse();

        // Validate the database is empty
        List<FormCell> formCellList = formCellRepository.findAll();
        assertThat(formCellList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormCell() throws Exception {
        // Initialize the database
        formCellRepository.saveAndFlush(formCell);
        formCellSearchRepository.save(formCell);

        // Search the formCell
        restFormCellMockMvc.perform(get("/api/_search/form-cells?query=id:" + formCell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formCell.getId().intValue())))
            .andExpect(jsonPath("$.[*].cellCode").value(hasItem(DEFAULT_CELL_CODE.toString())))
            .andExpect(jsonPath("$.[*].useConfidenceGrade").value(hasItem(DEFAULT_USE_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].inputConfidenceGrade").value(hasItem(DEFAULT_INPUT_CONFIDENCE_GRADE.booleanValue())))
            .andExpect(jsonPath("$.[*].confidenceGradeInputCode").value(hasItem(DEFAULT_CONFIDENCE_GRADE_INPUT_CODE.toString())))
            .andExpect(jsonPath("$.[*].rowHeadingSource").value(hasItem(DEFAULT_ROW_HEADING_SOURCE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnHeadingSource").value(hasItem(DEFAULT_COLUMN_HEADING_SOURCE.booleanValue())))
            .andExpect(jsonPath("$.[*].groupDescriptionCode").value(hasItem(DEFAULT_GROUP_DESCRIPTION_CODE.toString())))
            .andExpect(jsonPath("$.[*].row").value(hasItem(DEFAULT_ROW.toString())))
            .andExpect(jsonPath("$.[*].formColumn").value(hasItem(DEFAULT_FORM_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].rowSpan").value(hasItem(DEFAULT_ROW_SPAN.toString())))
            .andExpect(jsonPath("$.[*].formColumnSpan").value(hasItem(DEFAULT_FORM_COLUMN_SPAN.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormCell.class);
    }
}
