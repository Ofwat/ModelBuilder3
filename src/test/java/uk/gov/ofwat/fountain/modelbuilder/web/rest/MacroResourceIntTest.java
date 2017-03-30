package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.Macro;
import uk.gov.ofwat.fountain.modelbuilder.repository.MacroRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.MacroSearchRepository;
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
 * Test class for the MacroResource REST controller.
 *
 * @see MacroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class MacroResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONAL_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONAL_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_CODE = "BBBBBBBBBB";

    @Autowired
    private MacroRepository macroRepository;

    @Autowired
    private MacroSearchRepository macroSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMacroMockMvc;

    private Macro macro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MacroResource macroResource = new MacroResource(macroRepository, macroSearchRepository);
        this.restMacroMockMvc = MockMvcBuilders.standaloneSetup(macroResource)
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
    public static Macro createEntity(EntityManager em) {
        Macro macro = new Macro()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .block(DEFAULT_BLOCK)
                .conditionalItemCode(DEFAULT_CONDITIONAL_ITEM_CODE)
                .pageCode(DEFAULT_PAGE_CODE);
        return macro;
    }

    @Before
    public void initTest() {
        macroSearchRepository.deleteAll();
        macro = createEntity(em);
    }

    @Test
    @Transactional
    public void createMacro() throws Exception {
        int databaseSizeBeforeCreate = macroRepository.findAll().size();

        // Create the Macro

        restMacroMockMvc.perform(post("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(macro)))
            .andExpect(status().isCreated());

        // Validate the Macro in the database
        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeCreate + 1);
        Macro testMacro = macroList.get(macroList.size() - 1);
        assertThat(testMacro.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMacro.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMacro.getBlock()).isEqualTo(DEFAULT_BLOCK);
        assertThat(testMacro.getConditionalItemCode()).isEqualTo(DEFAULT_CONDITIONAL_ITEM_CODE);
        assertThat(testMacro.getPageCode()).isEqualTo(DEFAULT_PAGE_CODE);

        // Validate the Macro in Elasticsearch
        Macro macroEs = macroSearchRepository.findOne(testMacro.getId());
        assertThat(macroEs).isEqualToComparingFieldByField(testMacro);
    }

    @Test
    @Transactional
    public void createMacroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = macroRepository.findAll().size();

        // Create the Macro with an existing ID
        Macro existingMacro = new Macro();
        existingMacro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMacroMockMvc.perform(post("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMacro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroRepository.findAll().size();
        // set the field null
        macro.setName(null);

        // Create the Macro, which fails.

        restMacroMockMvc.perform(post("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(macro)))
            .andExpect(status().isBadRequest());

        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroRepository.findAll().size();
        // set the field null
        macro.setDescription(null);

        // Create the Macro, which fails.

        restMacroMockMvc.perform(post("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(macro)))
            .andExpect(status().isBadRequest());

        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBlockIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroRepository.findAll().size();
        // set the field null
        macro.setBlock(null);

        // Create the Macro, which fails.

        restMacroMockMvc.perform(post("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(macro)))
            .andExpect(status().isBadRequest());

        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMacros() throws Exception {
        // Initialize the database
        macroRepository.saveAndFlush(macro);

        // Get all the macroList
        restMacroMockMvc.perform(get("/api/macros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macro.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].block").value(hasItem(DEFAULT_BLOCK.toString())))
            .andExpect(jsonPath("$.[*].conditionalItemCode").value(hasItem(DEFAULT_CONDITIONAL_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].pageCode").value(hasItem(DEFAULT_PAGE_CODE.toString())));
    }

    @Test
    @Transactional
    public void getMacro() throws Exception {
        // Initialize the database
        macroRepository.saveAndFlush(macro);

        // Get the macro
        restMacroMockMvc.perform(get("/api/macros/{id}", macro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(macro.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.block").value(DEFAULT_BLOCK.toString()))
            .andExpect(jsonPath("$.conditionalItemCode").value(DEFAULT_CONDITIONAL_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.pageCode").value(DEFAULT_PAGE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMacro() throws Exception {
        // Get the macro
        restMacroMockMvc.perform(get("/api/macros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMacro() throws Exception {
        // Initialize the database
        macroRepository.saveAndFlush(macro);
        macroSearchRepository.save(macro);
        int databaseSizeBeforeUpdate = macroRepository.findAll().size();

        // Update the macro
        Macro updatedMacro = macroRepository.findOne(macro.getId());
        updatedMacro
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .block(UPDATED_BLOCK)
                .conditionalItemCode(UPDATED_CONDITIONAL_ITEM_CODE)
                .pageCode(UPDATED_PAGE_CODE);

        restMacroMockMvc.perform(put("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMacro)))
            .andExpect(status().isOk());

        // Validate the Macro in the database
        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeUpdate);
        Macro testMacro = macroList.get(macroList.size() - 1);
        assertThat(testMacro.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMacro.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMacro.getBlock()).isEqualTo(UPDATED_BLOCK);
        assertThat(testMacro.getConditionalItemCode()).isEqualTo(UPDATED_CONDITIONAL_ITEM_CODE);
        assertThat(testMacro.getPageCode()).isEqualTo(UPDATED_PAGE_CODE);

        // Validate the Macro in Elasticsearch
        Macro macroEs = macroSearchRepository.findOne(testMacro.getId());
        assertThat(macroEs).isEqualToComparingFieldByField(testMacro);
    }

    @Test
    @Transactional
    public void updateNonExistingMacro() throws Exception {
        int databaseSizeBeforeUpdate = macroRepository.findAll().size();

        // Create the Macro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMacroMockMvc.perform(put("/api/macros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(macro)))
            .andExpect(status().isCreated());

        // Validate the Macro in the database
        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMacro() throws Exception {
        // Initialize the database
        macroRepository.saveAndFlush(macro);
        macroSearchRepository.save(macro);
        int databaseSizeBeforeDelete = macroRepository.findAll().size();

        // Get the macro
        restMacroMockMvc.perform(delete("/api/macros/{id}", macro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean macroExistsInEs = macroSearchRepository.exists(macro.getId());
        assertThat(macroExistsInEs).isFalse();

        // Validate the database is empty
        List<Macro> macroList = macroRepository.findAll();
        assertThat(macroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMacro() throws Exception {
        // Initialize the database
        macroRepository.saveAndFlush(macro);
        macroSearchRepository.save(macro);

        // Search the macro
        restMacroMockMvc.perform(get("/api/_search/macros?query=id:" + macro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macro.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].block").value(hasItem(DEFAULT_BLOCK.toString())))
            .andExpect(jsonPath("$.[*].conditionalItemCode").value(hasItem(DEFAULT_CONDITIONAL_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].pageCode").value(hasItem(DEFAULT_PAGE_CODE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Macro.class);
    }
}
