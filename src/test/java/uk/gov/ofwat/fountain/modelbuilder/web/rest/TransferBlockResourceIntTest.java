package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import uk.gov.ofwat.fountain.modelbuilder.ModelBuilderApp;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlock;
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockRepository;
import uk.gov.ofwat.fountain.modelbuilder.repository.search.TransferBlockSearchRepository;
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
 * Test class for the TransferBlockResource REST controller.
 *
 * @see TransferBlockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelBuilderApp.class)
public class TransferBlockResourceIntTest {

    @Autowired
    private TransferBlockRepository transferBlockRepository;

    @Autowired
    private TransferBlockSearchRepository transferBlockSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransferBlockMockMvc;

    private TransferBlock transferBlock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TransferBlockResource transferBlockResource = new TransferBlockResource(transferBlockRepository, transferBlockSearchRepository);
        this.restTransferBlockMockMvc = MockMvcBuilders.standaloneSetup(transferBlockResource)
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
    public static TransferBlock createEntity(EntityManager em) {
        TransferBlock transferBlock = new TransferBlock();
        return transferBlock;
    }

    @Before
    public void initTest() {
        transferBlockSearchRepository.deleteAll();
        transferBlock = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransferBlock() throws Exception {
        int databaseSizeBeforeCreate = transferBlockRepository.findAll().size();

        // Create the TransferBlock

        restTransferBlockMockMvc.perform(post("/api/transfer-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferBlock)))
            .andExpect(status().isCreated());

        // Validate the TransferBlock in the database
        List<TransferBlock> transferBlockList = transferBlockRepository.findAll();
        assertThat(transferBlockList).hasSize(databaseSizeBeforeCreate + 1);
        TransferBlock testTransferBlock = transferBlockList.get(transferBlockList.size() - 1);

        // Validate the TransferBlock in Elasticsearch
        TransferBlock transferBlockEs = transferBlockSearchRepository.findOne(testTransferBlock.getId());
        assertThat(transferBlockEs).isEqualToComparingFieldByField(testTransferBlock);
    }

    @Test
    @Transactional
    public void createTransferBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferBlockRepository.findAll().size();

        // Create the TransferBlock with an existing ID
        TransferBlock existingTransferBlock = new TransferBlock();
        existingTransferBlock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferBlockMockMvc.perform(post("/api/transfer-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTransferBlock)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransferBlock> transferBlockList = transferBlockRepository.findAll();
        assertThat(transferBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransferBlocks() throws Exception {
        // Initialize the database
        transferBlockRepository.saveAndFlush(transferBlock);

        // Get all the transferBlockList
        restTransferBlockMockMvc.perform(get("/api/transfer-blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferBlock.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTransferBlock() throws Exception {
        // Initialize the database
        transferBlockRepository.saveAndFlush(transferBlock);

        // Get the transferBlock
        restTransferBlockMockMvc.perform(get("/api/transfer-blocks/{id}", transferBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transferBlock.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransferBlock() throws Exception {
        // Get the transferBlock
        restTransferBlockMockMvc.perform(get("/api/transfer-blocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransferBlock() throws Exception {
        // Initialize the database
        transferBlockRepository.saveAndFlush(transferBlock);
        transferBlockSearchRepository.save(transferBlock);
        int databaseSizeBeforeUpdate = transferBlockRepository.findAll().size();

        // Update the transferBlock
        TransferBlock updatedTransferBlock = transferBlockRepository.findOne(transferBlock.getId());

        restTransferBlockMockMvc.perform(put("/api/transfer-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransferBlock)))
            .andExpect(status().isOk());

        // Validate the TransferBlock in the database
        List<TransferBlock> transferBlockList = transferBlockRepository.findAll();
        assertThat(transferBlockList).hasSize(databaseSizeBeforeUpdate);
        TransferBlock testTransferBlock = transferBlockList.get(transferBlockList.size() - 1);

        // Validate the TransferBlock in Elasticsearch
        TransferBlock transferBlockEs = transferBlockSearchRepository.findOne(testTransferBlock.getId());
        assertThat(transferBlockEs).isEqualToComparingFieldByField(testTransferBlock);
    }

    @Test
    @Transactional
    public void updateNonExistingTransferBlock() throws Exception {
        int databaseSizeBeforeUpdate = transferBlockRepository.findAll().size();

        // Create the TransferBlock

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransferBlockMockMvc.perform(put("/api/transfer-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferBlock)))
            .andExpect(status().isCreated());

        // Validate the TransferBlock in the database
        List<TransferBlock> transferBlockList = transferBlockRepository.findAll();
        assertThat(transferBlockList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransferBlock() throws Exception {
        // Initialize the database
        transferBlockRepository.saveAndFlush(transferBlock);
        transferBlockSearchRepository.save(transferBlock);
        int databaseSizeBeforeDelete = transferBlockRepository.findAll().size();

        // Get the transferBlock
        restTransferBlockMockMvc.perform(delete("/api/transfer-blocks/{id}", transferBlock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transferBlockExistsInEs = transferBlockSearchRepository.exists(transferBlock.getId());
        assertThat(transferBlockExistsInEs).isFalse();

        // Validate the database is empty
        List<TransferBlock> transferBlockList = transferBlockRepository.findAll();
        assertThat(transferBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransferBlock() throws Exception {
        // Initialize the database
        transferBlockRepository.saveAndFlush(transferBlock);
        transferBlockSearchRepository.save(transferBlock);

        // Search the transferBlock
        restTransferBlockMockMvc.perform(get("/api/_search/transfer-blocks?query=id:" + transferBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferBlock.getId().intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferBlock.class);
    }
}
