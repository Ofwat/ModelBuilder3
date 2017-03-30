package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage;
import uk.gov.ofwat.fountain.modelbuilder.repository.CompanyPageRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.CompanyPageSearchRepository;
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
 * Test class for the CompanyPageResource REST controller.
 *
 * @see CompanyPageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class CompanyPageResourceIntTest {

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_CODE = "BBBBBBBBBB";

    @Autowired
    private CompanyPageRepository companyPageRepository;

    @Autowired
    private CompanyPageSearchRepository companyPageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyPageMockMvc;

    private CompanyPage companyPage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CompanyPageResource companyPageResource = new CompanyPageResource(companyPageRepository, companyPageSearchRepository);
        this.restCompanyPageMockMvc = MockMvcBuilders.standaloneSetup(companyPageResource)
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
    public static CompanyPage createEntity(EntityManager em) {
        CompanyPage companyPage = new CompanyPage()
                .companyCode(DEFAULT_COMPANY_CODE)
                .pageCode(DEFAULT_PAGE_CODE);
        return companyPage;
    }

    @Before
    public void initTest() {
        companyPageSearchRepository.deleteAll();
        companyPage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyPage() throws Exception {
        int databaseSizeBeforeCreate = companyPageRepository.findAll().size();

        // Create the CompanyPage

        restCompanyPageMockMvc.perform(post("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPage)))
            .andExpect(status().isCreated());

        // Validate the CompanyPage in the database
        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyPage testCompanyPage = companyPageList.get(companyPageList.size() - 1);
        assertThat(testCompanyPage.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyPage.getPageCode()).isEqualTo(DEFAULT_PAGE_CODE);

        // Validate the CompanyPage in Elasticsearch
        CompanyPage companyPageEs = companyPageSearchRepository.findOne(testCompanyPage.getId());
        assertThat(companyPageEs).isEqualToComparingFieldByField(testCompanyPage);
    }

    @Test
    @Transactional
    public void createCompanyPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyPageRepository.findAll().size();

        // Create the CompanyPage with an existing ID
        CompanyPage existingCompanyPage = new CompanyPage();
        existingCompanyPage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyPageMockMvc.perform(post("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCompanyPage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCompanyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyPageRepository.findAll().size();
        // set the field null
        companyPage.setCompanyCode(null);

        // Create the CompanyPage, which fails.

        restCompanyPageMockMvc.perform(post("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPage)))
            .andExpect(status().isBadRequest());

        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyPageRepository.findAll().size();
        // set the field null
        companyPage.setPageCode(null);

        // Create the CompanyPage, which fails.

        restCompanyPageMockMvc.perform(post("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPage)))
            .andExpect(status().isBadRequest());

        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyPages() throws Exception {
        // Initialize the database
        companyPageRepository.saveAndFlush(companyPage);

        // Get all the companyPageList
        restCompanyPageMockMvc.perform(get("/api/company-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].pageCode").value(hasItem(DEFAULT_PAGE_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCompanyPage() throws Exception {
        // Initialize the database
        companyPageRepository.saveAndFlush(companyPage);

        // Get the companyPage
        restCompanyPageMockMvc.perform(get("/api/company-pages/{id}", companyPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyPage.getId().intValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.pageCode").value(DEFAULT_PAGE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyPage() throws Exception {
        // Get the companyPage
        restCompanyPageMockMvc.perform(get("/api/company-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyPage() throws Exception {
        // Initialize the database
        companyPageRepository.saveAndFlush(companyPage);
        companyPageSearchRepository.save(companyPage);
        int databaseSizeBeforeUpdate = companyPageRepository.findAll().size();

        // Update the companyPage
        CompanyPage updatedCompanyPage = companyPageRepository.findOne(companyPage.getId());
        updatedCompanyPage
                .companyCode(UPDATED_COMPANY_CODE)
                .pageCode(UPDATED_PAGE_CODE);

        restCompanyPageMockMvc.perform(put("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyPage)))
            .andExpect(status().isOk());

        // Validate the CompanyPage in the database
        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeUpdate);
        CompanyPage testCompanyPage = companyPageList.get(companyPageList.size() - 1);
        assertThat(testCompanyPage.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyPage.getPageCode()).isEqualTo(UPDATED_PAGE_CODE);

        // Validate the CompanyPage in Elasticsearch
        CompanyPage companyPageEs = companyPageSearchRepository.findOne(testCompanyPage.getId());
        assertThat(companyPageEs).isEqualToComparingFieldByField(testCompanyPage);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyPage() throws Exception {
        int databaseSizeBeforeUpdate = companyPageRepository.findAll().size();

        // Create the CompanyPage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyPageMockMvc.perform(put("/api/company-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPage)))
            .andExpect(status().isCreated());

        // Validate the CompanyPage in the database
        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyPage() throws Exception {
        // Initialize the database
        companyPageRepository.saveAndFlush(companyPage);
        companyPageSearchRepository.save(companyPage);
        int databaseSizeBeforeDelete = companyPageRepository.findAll().size();

        // Get the companyPage
        restCompanyPageMockMvc.perform(delete("/api/company-pages/{id}", companyPage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyPageExistsInEs = companyPageSearchRepository.exists(companyPage.getId());
        assertThat(companyPageExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyPage> companyPageList = companyPageRepository.findAll();
        assertThat(companyPageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyPage() throws Exception {
        // Initialize the database
        companyPageRepository.saveAndFlush(companyPage);
        companyPageSearchRepository.save(companyPage);

        // Search the companyPage
        restCompanyPageMockMvc.perform(get("/api/_search/company-pages?query=id:" + companyPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].pageCode").value(hasItem(DEFAULT_PAGE_CODE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyPage.class);
    }
}
