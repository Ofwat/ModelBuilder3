package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.TextBlock;
import uk.gov.ofwat.fountain.modelbuilder.repository.TextBlockRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TextBlockSearchRepository;
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
 * Test class for the TextBlockResource REST controller.
 *
 * @see TextBlockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class TextBlockResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VERSION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_FORMAT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FORMAT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_TYPE_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RETIRED = false;
    private static final Boolean UPDATED_RETIRED = true;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private TextBlockRepository textBlockRepository;

    @Autowired
    private TextBlockSearchRepository textBlockSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTextBlockMockMvc;

    private TextBlock textBlock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TextBlockResource textBlockResource = new TextBlockResource(textBlockRepository, textBlockSearchRepository);
        this.restTextBlockMockMvc = MockMvcBuilders.standaloneSetup(textBlockResource)
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
    public static TextBlock createEntity(EntityManager em) {
        TextBlock textBlock = new TextBlock()
                .description(DEFAULT_DESCRIPTION)
                .versionNumber(DEFAULT_VERSION_NUMBER)
                .textFormatCode(DEFAULT_TEXT_FORMAT_CODE)
                .textTypeCode(DEFAULT_TEXT_TYPE_CODE)
                .retired(DEFAULT_RETIRED)
                .data(DEFAULT_DATA);
        return textBlock;
    }

    @Before
    public void initTest() {
        textBlockSearchRepository.deleteAll();
        textBlock = createEntity(em);
    }

    @Test
    @Transactional
    public void createTextBlock() throws Exception {
        int databaseSizeBeforeCreate = textBlockRepository.findAll().size();

        // Create the TextBlock

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isCreated());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeCreate + 1);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTextBlock.getVersionNumber()).isEqualTo(DEFAULT_VERSION_NUMBER);
        assertThat(testTextBlock.getTextFormatCode()).isEqualTo(DEFAULT_TEXT_FORMAT_CODE);
        assertThat(testTextBlock.getTextTypeCode()).isEqualTo(DEFAULT_TEXT_TYPE_CODE);
        assertThat(testTextBlock.isRetired()).isEqualTo(DEFAULT_RETIRED);
        assertThat(testTextBlock.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the TextBlock in Elasticsearch
        TextBlock textBlockEs = textBlockSearchRepository.findOne(testTextBlock.getId());
        assertThat(textBlockEs).isEqualToComparingFieldByField(testTextBlock);
    }

    @Test
    @Transactional
    public void createTextBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = textBlockRepository.findAll().size();

        // Create the TextBlock with an existing ID
        TextBlock existingTextBlock = new TextBlock();
        existingTextBlock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTextBlock)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = textBlockRepository.findAll().size();
        // set the field null
        textBlock.setDescription(null);

        // Create the TextBlock, which fails.

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isBadRequest());

        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = textBlockRepository.findAll().size();
        // set the field null
        textBlock.setVersionNumber(null);

        // Create the TextBlock, which fails.

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isBadRequest());

        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextFormatCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = textBlockRepository.findAll().size();
        // set the field null
        textBlock.setTextFormatCode(null);

        // Create the TextBlock, which fails.

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isBadRequest());

        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = textBlockRepository.findAll().size();
        // set the field null
        textBlock.setTextTypeCode(null);

        // Create the TextBlock, which fails.

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isBadRequest());

        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = textBlockRepository.findAll().size();
        // set the field null
        textBlock.setData(null);

        // Create the TextBlock, which fails.

        restTextBlockMockMvc.perform(post("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isBadRequest());

        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTextBlocks() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList
        restTextBlockMockMvc.perform(get("/api/text-blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].textFormatCode").value(hasItem(DEFAULT_TEXT_FORMAT_CODE.toString())))
            .andExpect(jsonPath("$.[*].textTypeCode").value(hasItem(DEFAULT_TEXT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].retired").value(hasItem(DEFAULT_RETIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void getTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get the textBlock
        restTextBlockMockMvc.perform(get("/api/text-blocks/{id}", textBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(textBlock.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.versionNumber").value(DEFAULT_VERSION_NUMBER.toString()))
            .andExpect(jsonPath("$.textFormatCode").value(DEFAULT_TEXT_FORMAT_CODE.toString()))
            .andExpect(jsonPath("$.textTypeCode").value(DEFAULT_TEXT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.retired").value(DEFAULT_RETIRED.booleanValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTextBlock() throws Exception {
        // Get the textBlock
        restTextBlockMockMvc.perform(get("/api/text-blocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);
        textBlockSearchRepository.save(textBlock);
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();

        // Update the textBlock
        TextBlock updatedTextBlock = textBlockRepository.findOne(textBlock.getId());
        updatedTextBlock
                .description(UPDATED_DESCRIPTION)
                .versionNumber(UPDATED_VERSION_NUMBER)
                .textFormatCode(UPDATED_TEXT_FORMAT_CODE)
                .textTypeCode(UPDATED_TEXT_TYPE_CODE)
                .retired(UPDATED_RETIRED)
                .data(UPDATED_DATA);

        restTextBlockMockMvc.perform(put("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTextBlock)))
            .andExpect(status().isOk());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTextBlock.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);
        assertThat(testTextBlock.getTextFormatCode()).isEqualTo(UPDATED_TEXT_FORMAT_CODE);
        assertThat(testTextBlock.getTextTypeCode()).isEqualTo(UPDATED_TEXT_TYPE_CODE);
        assertThat(testTextBlock.isRetired()).isEqualTo(UPDATED_RETIRED);
        assertThat(testTextBlock.getData()).isEqualTo(UPDATED_DATA);

        // Validate the TextBlock in Elasticsearch
        TextBlock textBlockEs = textBlockSearchRepository.findOne(testTextBlock.getId());
        assertThat(textBlockEs).isEqualToComparingFieldByField(testTextBlock);
    }

    @Test
    @Transactional
    public void updateNonExistingTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();

        // Create the TextBlock

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTextBlockMockMvc.perform(put("/api/text-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textBlock)))
            .andExpect(status().isCreated());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);
        textBlockSearchRepository.save(textBlock);
        int databaseSizeBeforeDelete = textBlockRepository.findAll().size();

        // Get the textBlock
        restTextBlockMockMvc.perform(delete("/api/text-blocks/{id}", textBlock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean textBlockExistsInEs = textBlockSearchRepository.exists(textBlock.getId());
        assertThat(textBlockExistsInEs).isFalse();

        // Validate the database is empty
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);
        textBlockSearchRepository.save(textBlock);

        // Search the textBlock
        restTextBlockMockMvc.perform(get("/api/_search/text-blocks?query=id:" + textBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].textFormatCode").value(hasItem(DEFAULT_TEXT_FORMAT_CODE.toString())))
            .andExpect(jsonPath("$.[*].textTypeCode").value(hasItem(DEFAULT_TEXT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].retired").value(hasItem(DEFAULT_RETIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextBlock.class);
    }
}
