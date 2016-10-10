package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.TrlprjApp;

import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.service.TechnologyService;

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

import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;
/**
 * Test class for the TechnologyResource REST controller.
 *
 * @see TechnologyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrlprjApp.class)
public class TechnologyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_DESCRIPT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_IS_KEY = false;
    private static final Boolean UPDATED_IS_KEY = true;

    private static final TCL DEFAULT_TCL = TCL.TCLA;
    private static final TCL UPDATED_TCL = TCL.TCLB;

    private static final TRL DEFAULT_TRL = TRL.TRL1;
    private static final TRL UPDATED_TRL = TRL.TRL2;

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private TechnologyService technologyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TechnologyResource technologyResource = new TechnologyResource();
        ReflectionTestUtils.setField(technologyResource, "technologyService", technologyService);
        this.restTechnologyMockMvc = MockMvcBuilders.standaloneSetup(technologyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createEntity(EntityManager em) {
        Technology technology = new Technology()
                .name(DEFAULT_NAME)
                .descript(DEFAULT_DESCRIPT)
                .isKey(DEFAULT_IS_KEY)
                .tcl(DEFAULT_TCL)
                .trl(DEFAULT_TRL);
        return technology;
    }

    @Before
    public void initTest() {
        technology = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnology() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology

        restTechnologyMockMvc.perform(post("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technology)))
                .andExpect(status().isCreated());

        // Validate the Technology in the database
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeCreate + 1);
        Technology testTechnology = technologies.get(technologies.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTechnology.getDescript()).isEqualTo(DEFAULT_DESCRIPT);
        assertThat(testTechnology.isIsKey()).isEqualTo(DEFAULT_IS_KEY);
        assertThat(testTechnology.getTcl()).isEqualTo(DEFAULT_TCL);
        assertThat(testTechnology.getTrl()).isEqualTo(DEFAULT_TRL);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setName(null);

        // Create the Technology, which fails.

        restTechnologyMockMvc.perform(post("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technology)))
                .andExpect(status().isBadRequest());

        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setIsKey(null);

        // Create the Technology, which fails.

        restTechnologyMockMvc.perform(post("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technology)))
                .andExpect(status().isBadRequest());

        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnologies() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get all the technologies
        restTechnologyMockMvc.perform(get("/api/technologies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].descript").value(hasItem(DEFAULT_DESCRIPT.toString())))
                .andExpect(jsonPath("$.[*].isKey").value(hasItem(DEFAULT_IS_KEY.booleanValue())))
                .andExpect(jsonPath("$.[*].tcl").value(hasItem(DEFAULT_TCL.toString())))
                .andExpect(jsonPath("$.[*].trl").value(hasItem(DEFAULT_TRL.toString())));
    }

    @Test
    @Transactional
    public void getTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.descript").value(DEFAULT_DESCRIPT.toString()))
            .andExpect(jsonPath("$.isKey").value(DEFAULT_IS_KEY.booleanValue()))
            .andExpect(jsonPath("$.tcl").value(DEFAULT_TCL.toString()))
            .andExpect(jsonPath("$.trl").value(DEFAULT_TRL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnology() throws Exception {
        // Initialize the database
        technologyService.save(technology);

        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Update the technology
        Technology updatedTechnology = technologyRepository.findOne(technology.getId());
        updatedTechnology
                .name(UPDATED_NAME)
                .descript(UPDATED_DESCRIPT)
                .isKey(UPDATED_IS_KEY)
                .tcl(UPDATED_TCL)
                .trl(UPDATED_TRL);

        restTechnologyMockMvc.perform(put("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTechnology)))
                .andExpect(status().isOk());

        // Validate the Technology in the database
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeUpdate);
        Technology testTechnology = technologies.get(technologies.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTechnology.getDescript()).isEqualTo(UPDATED_DESCRIPT);
        assertThat(testTechnology.isIsKey()).isEqualTo(UPDATED_IS_KEY);
        assertThat(testTechnology.getTcl()).isEqualTo(UPDATED_TCL);
        assertThat(testTechnology.getTrl()).isEqualTo(UPDATED_TRL);
    }

    @Test
    @Transactional
    public void deleteTechnology() throws Exception {
        // Initialize the database
        technologyService.save(technology);

        int databaseSizeBeforeDelete = technologyRepository.findAll().size();

        // Get the technology
        restTechnologyMockMvc.perform(delete("/api/technologies/{id}", technology.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
