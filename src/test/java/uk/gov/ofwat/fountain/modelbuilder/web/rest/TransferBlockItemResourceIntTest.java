package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockItem;
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockItemRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferBlockItemSearchRepository;
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
 * Test class for the TransferBlockItemResource REST controller.
 *
 * @see TransferBlockItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class TransferBlockItemResourceIntTest {

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_TYPE = "BBBBBBBBBB";

    @Autowired
    private TransferBlockItemRepository transferBlockItemRepository;

    @Autowired
    private TransferBlockItemSearchRepository transferBlockItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransferBlockItemMockMvc;

    private TransferBlockItem transferBlockItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TransferBlockItemResource transferBlockItemResource = new TransferBlockItemResource(transferBlockItemRepository, transferBlockItemSearchRepository);
        this.restTransferBlockItemMockMvc = MockMvcBuilders.standaloneSetup(transferBlockItemResource)
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
    public static TransferBlockItem createEntity(EntityManager em) {
        TransferBlockItem transferBlockItem = new TransferBlockItem()
                .itemCode(DEFAULT_ITEM_CODE)
                .companyType(DEFAULT_COMPANY_TYPE);
        return transferBlockItem;
    }

    @Before
    public void initTest() {
        transferBlockItemSearchRepository.deleteAll();
        transferBlockItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransferBlockItem() throws Exception {
        int databaseSizeBeforeCreate = transferBlockItemRepository.findAll().size();

        // Create the TransferBlockItem

        restTransferBlockItemMockMvc.perform(post("/api/transfer-block-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferBlockItem)))
            .andExpect(status().isCreated());

        // Validate the TransferBlockItem in the database
        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeCreate + 1);
        TransferBlockItem testTransferBlockItem = transferBlockItemList.get(transferBlockItemList.size() - 1);
        assertThat(testTransferBlockItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testTransferBlockItem.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);

        // Validate the TransferBlockItem in Elasticsearch
        TransferBlockItem transferBlockItemEs = transferBlockItemSearchRepository.findOne(testTransferBlockItem.getId());
        assertThat(transferBlockItemEs).isEqualToComparingFieldByField(testTransferBlockItem);
    }

    @Test
    @Transactional
    public void createTransferBlockItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferBlockItemRepository.findAll().size();

        // Create the TransferBlockItem with an existing ID
        TransferBlockItem existingTransferBlockItem = new TransferBlockItem();
        existingTransferBlockItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferBlockItemMockMvc.perform(post("/api/transfer-block-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTransferBlockItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkItemCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferBlockItemRepository.findAll().size();
        // set the field null
        transferBlockItem.setItemCode(null);

        // Create the TransferBlockItem, which fails.

        restTransferBlockItemMockMvc.perform(post("/api/transfer-block-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferBlockItem)))
            .andExpect(status().isBadRequest());

        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransferBlockItems() throws Exception {
        // Initialize the database
        transferBlockItemRepository.saveAndFlush(transferBlockItem);

        // Get all the transferBlockItemList
        restTransferBlockItemMockMvc.perform(get("/api/transfer-block-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferBlockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTransferBlockItem() throws Exception {
        // Initialize the database
        transferBlockItemRepository.saveAndFlush(transferBlockItem);

        // Get the transferBlockItem
        restTransferBlockItemMockMvc.perform(get("/api/transfer-block-items/{id}", transferBlockItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transferBlockItem.getId().intValue()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransferBlockItem() throws Exception {
        // Get the transferBlockItem
        restTransferBlockItemMockMvc.perform(get("/api/transfer-block-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransferBlockItem() throws Exception {
        // Initialize the database
        transferBlockItemRepository.saveAndFlush(transferBlockItem);
        transferBlockItemSearchRepository.save(transferBlockItem);
        int databaseSizeBeforeUpdate = transferBlockItemRepository.findAll().size();

        // Update the transferBlockItem
        TransferBlockItem updatedTransferBlockItem = transferBlockItemRepository.findOne(transferBlockItem.getId());
        updatedTransferBlockItem
                .itemCode(UPDATED_ITEM_CODE)
                .companyType(UPDATED_COMPANY_TYPE);

        restTransferBlockItemMockMvc.perform(put("/api/transfer-block-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransferBlockItem)))
            .andExpect(status().isOk());

        // Validate the TransferBlockItem in the database
        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeUpdate);
        TransferBlockItem testTransferBlockItem = transferBlockItemList.get(transferBlockItemList.size() - 1);
        assertThat(testTransferBlockItem.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testTransferBlockItem.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);

        // Validate the TransferBlockItem in Elasticsearch
        TransferBlockItem transferBlockItemEs = transferBlockItemSearchRepository.findOne(testTransferBlockItem.getId());
        assertThat(transferBlockItemEs).isEqualToComparingFieldByField(testTransferBlockItem);
    }

    @Test
    @Transactional
    public void updateNonExistingTransferBlockItem() throws Exception {
        int databaseSizeBeforeUpdate = transferBlockItemRepository.findAll().size();

        // Create the TransferBlockItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransferBlockItemMockMvc.perform(put("/api/transfer-block-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferBlockItem)))
            .andExpect(status().isCreated());

        // Validate the TransferBlockItem in the database
        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransferBlockItem() throws Exception {
        // Initialize the database
        transferBlockItemRepository.saveAndFlush(transferBlockItem);
        transferBlockItemSearchRepository.save(transferBlockItem);
        int databaseSizeBeforeDelete = transferBlockItemRepository.findAll().size();

        // Get the transferBlockItem
        restTransferBlockItemMockMvc.perform(delete("/api/transfer-block-items/{id}", transferBlockItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transferBlockItemExistsInEs = transferBlockItemSearchRepository.exists(transferBlockItem.getId());
        assertThat(transferBlockItemExistsInEs).isFalse();

        // Validate the database is empty
        List<TransferBlockItem> transferBlockItemList = transferBlockItemRepository.findAll();
        assertThat(transferBlockItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransferBlockItem() throws Exception {
        // Initialize the database
        transferBlockItemRepository.saveAndFlush(transferBlockItem);
        transferBlockItemSearchRepository.save(transferBlockItem);

        // Search the transferBlockItem
        restTransferBlockItemMockMvc.perform(get("/api/_search/transfer-block-items?query=id:" + transferBlockItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferBlockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferBlockItem.class);
    }
}
