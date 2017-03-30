package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.Text;
import uk.gov.ofwat.fountain.modelbuilder.repository.TextRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TextSearchRepository;
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
 * Test class for the TextResource REST controller.
 *
 * @see TextResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class TextResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TextSearchRepository textSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTextMockMvc;

    private Text text;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TextResource textResource = new TextResource(textRepository, textSearchRepository);
        this.restTextMockMvc = MockMvcBuilders.standaloneSetup(textResource)
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
    public static Text createEntity(EntityManager em) {
        Text text = new Text()
                .code(DEFAULT_CODE);
        return text;
    }

    @Before
    public void initTest() {
        textSearchRepository.deleteAll();
        text = createEntity(em);
    }

    @Test
    @Transactional
    public void createText() throws Exception {
        int databaseSizeBeforeCreate = textRepository.findAll().size();

        // Create the Text

        restTextMockMvc.perform(post("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isCreated());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate + 1);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the Text in Elasticsearch
        Text textEs = textSearchRepository.findOne(testText.getId());
        assertThat(textEs).isEqualToComparingFieldByField(testText);
    }

    @Test
    @Transactional
    public void createTextWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = textRepository.findAll().size();

        // Create the Text with an existing ID
        Text existingText = new Text();
        existingText.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextMockMvc.perform(post("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingText)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = textRepository.findAll().size();
        // set the field null
        text.setCode(null);

        // Create the Text, which fails.

        restTextMockMvc.perform(post("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isBadRequest());

        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTexts() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList
        restTextMockMvc.perform(get("/api/texts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get the text
        restTextMockMvc.perform(get("/api/texts/{id}", text.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(text.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingText() throws Exception {
        // Get the text
        restTextMockMvc.perform(get("/api/texts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);
        textSearchRepository.save(text);
        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text
        Text updatedText = textRepository.findOne(text.getId());
        updatedText
                .code(UPDATED_CODE);

        restTextMockMvc.perform(put("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedText)))
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the Text in Elasticsearch
        Text textEs = textSearchRepository.findOne(testText.getId());
        assertThat(textEs).isEqualToComparingFieldByField(testText);
    }

    @Test
    @Transactional
    public void updateNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Create the Text

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTextMockMvc.perform(put("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isCreated());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);
        textSearchRepository.save(text);
        int databaseSizeBeforeDelete = textRepository.findAll().size();

        // Get the text
        restTextMockMvc.perform(delete("/api/texts/{id}", text.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean textExistsInEs = textSearchRepository.exists(text.getId());
        assertThat(textExistsInEs).isFalse();

        // Validate the database is empty
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);
        textSearchRepository.save(text);

        // Search the text
        restTextMockMvc.perform(get("/api/_search/texts?query=id:" + text.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Text.class);
    }
}
