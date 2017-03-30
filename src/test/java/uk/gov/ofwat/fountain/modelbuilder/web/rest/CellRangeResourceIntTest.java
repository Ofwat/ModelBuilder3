package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange;
import uk.gov.ofwat.fountain.modelbuilder.repository.CellRangeRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.CellRangeSearchRepository;
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
 * Test class for the CellRangeResource REST controller.
 *
 * @see CellRangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class CellRangeResourceIntTest {

    private static final String DEFAULT_START_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_START_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_END_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_END_YEAR = "BBBBBBBBBB";

    @Autowired
    private CellRangeRepository cellRangeRepository;

    @Autowired
    private CellRangeSearchRepository cellRangeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCellRangeMockMvc;

    private CellRange cellRange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CellRangeResource cellRangeResource = new CellRangeResource(cellRangeRepository, cellRangeSearchRepository);
        this.restCellRangeMockMvc = MockMvcBuilders.standaloneSetup(cellRangeResource)
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
    public static CellRange createEntity(EntityManager em) {
        CellRange cellRange = new CellRange()
                .startYear(DEFAULT_START_YEAR)
                .endYear(DEFAULT_END_YEAR);
        return cellRange;
    }

    @Before
    public void initTest() {
        cellRangeSearchRepository.deleteAll();
        cellRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createCellRange() throws Exception {
        int databaseSizeBeforeCreate = cellRangeRepository.findAll().size();

        // Create the CellRange

        restCellRangeMockMvc.perform(post("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cellRange)))
            .andExpect(status().isCreated());

        // Validate the CellRange in the database
        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeCreate + 1);
        CellRange testCellRange = cellRangeList.get(cellRangeList.size() - 1);
        assertThat(testCellRange.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testCellRange.getEndYear()).isEqualTo(DEFAULT_END_YEAR);

        // Validate the CellRange in Elasticsearch
        CellRange cellRangeEs = cellRangeSearchRepository.findOne(testCellRange.getId());
        assertThat(cellRangeEs).isEqualToComparingFieldByField(testCellRange);
    }

    @Test
    @Transactional
    public void createCellRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cellRangeRepository.findAll().size();

        // Create the CellRange with an existing ID
        CellRange existingCellRange = new CellRange();
        existingCellRange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCellRangeMockMvc.perform(post("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCellRange)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellRangeRepository.findAll().size();
        // set the field null
        cellRange.setStartYear(null);

        // Create the CellRange, which fails.

        restCellRangeMockMvc.perform(post("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cellRange)))
            .andExpect(status().isBadRequest());

        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellRangeRepository.findAll().size();
        // set the field null
        cellRange.setEndYear(null);

        // Create the CellRange, which fails.

        restCellRangeMockMvc.perform(post("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cellRange)))
            .andExpect(status().isBadRequest());

        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCellRanges() throws Exception {
        // Initialize the database
        cellRangeRepository.saveAndFlush(cellRange);

        // Get all the cellRangeList
        restCellRangeMockMvc.perform(get("/api/cell-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cellRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR.toString())))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR.toString())));
    }

    @Test
    @Transactional
    public void getCellRange() throws Exception {
        // Initialize the database
        cellRangeRepository.saveAndFlush(cellRange);

        // Get the cellRange
        restCellRangeMockMvc.perform(get("/api/cell-ranges/{id}", cellRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cellRange.getId().intValue()))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR.toString()))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCellRange() throws Exception {
        // Get the cellRange
        restCellRangeMockMvc.perform(get("/api/cell-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCellRange() throws Exception {
        // Initialize the database
        cellRangeRepository.saveAndFlush(cellRange);
        cellRangeSearchRepository.save(cellRange);
        int databaseSizeBeforeUpdate = cellRangeRepository.findAll().size();

        // Update the cellRange
        CellRange updatedCellRange = cellRangeRepository.findOne(cellRange.getId());
        updatedCellRange
                .startYear(UPDATED_START_YEAR)
                .endYear(UPDATED_END_YEAR);

        restCellRangeMockMvc.perform(put("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCellRange)))
            .andExpect(status().isOk());

        // Validate the CellRange in the database
        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeUpdate);
        CellRange testCellRange = cellRangeList.get(cellRangeList.size() - 1);
        assertThat(testCellRange.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testCellRange.getEndYear()).isEqualTo(UPDATED_END_YEAR);

        // Validate the CellRange in Elasticsearch
        CellRange cellRangeEs = cellRangeSearchRepository.findOne(testCellRange.getId());
        assertThat(cellRangeEs).isEqualToComparingFieldByField(testCellRange);
    }

    @Test
    @Transactional
    public void updateNonExistingCellRange() throws Exception {
        int databaseSizeBeforeUpdate = cellRangeRepository.findAll().size();

        // Create the CellRange

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCellRangeMockMvc.perform(put("/api/cell-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cellRange)))
            .andExpect(status().isCreated());

        // Validate the CellRange in the database
        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCellRange() throws Exception {
        // Initialize the database
        cellRangeRepository.saveAndFlush(cellRange);
        cellRangeSearchRepository.save(cellRange);
        int databaseSizeBeforeDelete = cellRangeRepository.findAll().size();

        // Get the cellRange
        restCellRangeMockMvc.perform(delete("/api/cell-ranges/{id}", cellRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cellRangeExistsInEs = cellRangeSearchRepository.exists(cellRange.getId());
        assertThat(cellRangeExistsInEs).isFalse();

        // Validate the database is empty
        List<CellRange> cellRangeList = cellRangeRepository.findAll();
        assertThat(cellRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCellRange() throws Exception {
        // Initialize the database
        cellRangeRepository.saveAndFlush(cellRange);
        cellRangeSearchRepository.save(cellRange);

        // Search the cellRange
        restCellRangeMockMvc.perform(get("/api/_search/cell-ranges?query=id:" + cellRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cellRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR.toString())))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CellRange.class);
    }
}
