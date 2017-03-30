package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.Heading;
import uk.gov.ofwat.fountain.modelbuilder.repository.HeadingRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.HeadingSearchRepository;
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
 * Test class for the HeadingResource REST controller.
 *
 * @see HeadingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class HeadingResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT = "BBBBBBBBBB";

    private static final String DEFAULT_ANNOTATION = "AAAAAAAAAA";
    private static final String UPDATED_ANNOTATION = "BBBBBBBBBB";

    @Autowired
    private HeadingRepository headingRepository;

    @Autowired
    private HeadingSearchRepository headingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHeadingMockMvc;

    private Heading heading;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            HeadingResource headingResource = new HeadingResource(headingRepository, headingSearchRepository);
        this.restHeadingMockMvc = MockMvcBuilders.standaloneSetup(headingResource)
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
    public static Heading createEntity(EntityManager em) {
        Heading heading = new Heading()
                .code(DEFAULT_CODE)
                .description(DEFAULT_DESCRIPTION)
                .notes(DEFAULT_NOTES)
                .parent(DEFAULT_PARENT)
                .annotation(DEFAULT_ANNOTATION);
        return heading;
    }

    @Before
    public void initTest() {
        headingSearchRepository.deleteAll();
        heading = createEntity(em);
    }

    @Test
    @Transactional
    public void createHeading() throws Exception {
        int databaseSizeBeforeCreate = headingRepository.findAll().size();

        // Create the Heading

        restHeadingMockMvc.perform(post("/api/headings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(heading)))
            .andExpect(status().isCreated());

        // Validate the Heading in the database
        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeCreate + 1);
        Heading testHeading = headingList.get(headingList.size() - 1);
        assertThat(testHeading.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testHeading.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHeading.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testHeading.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testHeading.getAnnotation()).isEqualTo(DEFAULT_ANNOTATION);

        // Validate the Heading in Elasticsearch
        Heading headingEs = headingSearchRepository.findOne(testHeading.getId());
        assertThat(headingEs).isEqualToComparingFieldByField(testHeading);
    }

    @Test
    @Transactional
    public void createHeadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = headingRepository.findAll().size();

        // Create the Heading with an existing ID
        Heading existingHeading = new Heading();
        existingHeading.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeadingMockMvc.perform(post("/api/headings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHeading)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = headingRepository.findAll().size();
        // set the field null
        heading.setCode(null);

        // Create the Heading, which fails.

        restHeadingMockMvc.perform(post("/api/headings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(heading)))
            .andExpect(status().isBadRequest());

        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeadings() throws Exception {
        // Initialize the database
        headingRepository.saveAndFlush(heading);

        // Get all the headingList
        restHeadingMockMvc.perform(get("/api/headings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heading.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())))
            .andExpect(jsonPath("$.[*].annotation").value(hasItem(DEFAULT_ANNOTATION.toString())));
    }

    @Test
    @Transactional
    public void getHeading() throws Exception {
        // Initialize the database
        headingRepository.saveAndFlush(heading);

        // Get the heading
        restHeadingMockMvc.perform(get("/api/headings/{id}", heading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(heading.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()))
            .andExpect(jsonPath("$.annotation").value(DEFAULT_ANNOTATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeading() throws Exception {
        // Get the heading
        restHeadingMockMvc.perform(get("/api/headings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeading() throws Exception {
        // Initialize the database
        headingRepository.saveAndFlush(heading);
        headingSearchRepository.save(heading);
        int databaseSizeBeforeUpdate = headingRepository.findAll().size();

        // Update the heading
        Heading updatedHeading = headingRepository.findOne(heading.getId());
        updatedHeading
                .code(UPDATED_CODE)
                .description(UPDATED_DESCRIPTION)
                .notes(UPDATED_NOTES)
                .parent(UPDATED_PARENT)
                .annotation(UPDATED_ANNOTATION);

        restHeadingMockMvc.perform(put("/api/headings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHeading)))
            .andExpect(status().isOk());

        // Validate the Heading in the database
        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeUpdate);
        Heading testHeading = headingList.get(headingList.size() - 1);
        assertThat(testHeading.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testHeading.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHeading.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testHeading.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testHeading.getAnnotation()).isEqualTo(UPDATED_ANNOTATION);

        // Validate the Heading in Elasticsearch
        Heading headingEs = headingSearchRepository.findOne(testHeading.getId());
        assertThat(headingEs).isEqualToComparingFieldByField(testHeading);
    }

    @Test
    @Transactional
    public void updateNonExistingHeading() throws Exception {
        int databaseSizeBeforeUpdate = headingRepository.findAll().size();

        // Create the Heading

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHeadingMockMvc.perform(put("/api/headings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(heading)))
            .andExpect(status().isCreated());

        // Validate the Heading in the database
        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHeading() throws Exception {
        // Initialize the database
        headingRepository.saveAndFlush(heading);
        headingSearchRepository.save(heading);
        int databaseSizeBeforeDelete = headingRepository.findAll().size();

        // Get the heading
        restHeadingMockMvc.perform(delete("/api/headings/{id}", heading.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean headingExistsInEs = headingSearchRepository.exists(heading.getId());
        assertThat(headingExistsInEs).isFalse();

        // Validate the database is empty
        List<Heading> headingList = headingRepository.findAll();
        assertThat(headingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHeading() throws Exception {
        // Initialize the database
        headingRepository.saveAndFlush(heading);
        headingSearchRepository.save(heading);

        // Search the heading
        restHeadingMockMvc.perform(get("/api/_search/headings?query=id:" + heading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heading.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())))
            .andExpect(jsonPath("$.[*].annotation").value(hasItem(DEFAULT_ANNOTATION.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Heading.class);
    }
}
