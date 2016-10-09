package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.TrlprjApp;

import com.caecc.trlprj.domain.Brahch;
import com.caecc.trlprj.repository.BrahchRepository;
import com.caecc.trlprj.service.BrahchService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BrahchResource REST controller.
 *
 * @see BrahchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrlprjApp.class)
public class BrahchResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private BrahchRepository brahchRepository;

    @Inject
    private BrahchService brahchService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBrahchMockMvc;

    private Brahch brahch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BrahchResource brahchResource = new BrahchResource();
        ReflectionTestUtils.setField(brahchResource, "brahchService", brahchService);
        this.restBrahchMockMvc = MockMvcBuilders.standaloneSetup(brahchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brahch createEntity(EntityManager em) {
        Brahch brahch = new Brahch()
                .name(DEFAULT_NAME);
        return brahch;
    }

    @Before
    public void initTest() {
        brahch = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrahch() throws Exception {
        int databaseSizeBeforeCreate = brahchRepository.findAll().size();

        // Create the Brahch

        restBrahchMockMvc.perform(post("/api/brahches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(brahch)))
                .andExpect(status().isCreated());

        // Validate the Brahch in the database
        List<Brahch> brahches = brahchRepository.findAll();
        assertThat(brahches).hasSize(databaseSizeBeforeCreate + 1);
        Brahch testBrahch = brahches.get(brahches.size() - 1);
        assertThat(testBrahch.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brahchRepository.findAll().size();
        // set the field null
        brahch.setName(null);

        // Create the Brahch, which fails.

        restBrahchMockMvc.perform(post("/api/brahches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(brahch)))
                .andExpect(status().isBadRequest());

        List<Brahch> brahches = brahchRepository.findAll();
        assertThat(brahches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrahches() throws Exception {
        // Initialize the database
        brahchRepository.saveAndFlush(brahch);

        // Get all the brahches
        restBrahchMockMvc.perform(get("/api/brahches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(brahch.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBrahch() throws Exception {
        // Initialize the database
        brahchRepository.saveAndFlush(brahch);

        // Get the brahch
        restBrahchMockMvc.perform(get("/api/brahches/{id}", brahch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brahch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrahch() throws Exception {
        // Get the brahch
        restBrahchMockMvc.perform(get("/api/brahches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrahch() throws Exception {
        // Initialize the database
        brahchService.save(brahch);

        int databaseSizeBeforeUpdate = brahchRepository.findAll().size();

        // Update the brahch
        Brahch updatedBrahch = brahchRepository.findOne(brahch.getId());
        updatedBrahch
                .name(UPDATED_NAME);

        restBrahchMockMvc.perform(put("/api/brahches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBrahch)))
                .andExpect(status().isOk());

        // Validate the Brahch in the database
        List<Brahch> brahches = brahchRepository.findAll();
        assertThat(brahches).hasSize(databaseSizeBeforeUpdate);
        Brahch testBrahch = brahches.get(brahches.size() - 1);
        assertThat(testBrahch.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteBrahch() throws Exception {
        // Initialize the database
        brahchService.save(brahch);

        int databaseSizeBeforeDelete = brahchRepository.findAll().size();

        // Get the brahch
        restBrahchMockMvc.perform(delete("/api/brahches/{id}", brahch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Brahch> brahches = brahchRepository.findAll();
        assertThat(brahches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
