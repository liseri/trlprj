package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.TrlprjApp;

import com.caecc.trlprj.domain.Note;
import com.caecc.trlprj.repository.NoteRepository;
import com.caecc.trlprj.service.NoteService;

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

import com.caecc.trlprj.domain.enumeration.NoteType;
/**
 * Test class for the NoteResource REST controller.
 *
 * @see NoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrlprjApp.class)
public class NoteResourceIntTest {


    private static final Long DEFAULT_TECH_ID = 1L;
    private static final Long UPDATED_TECH_ID = 2L;

    private static final NoteType DEFAULT_NOTE_TYPE = NoteType.TKEYTECH;
    private static final NoteType UPDATED_NOTE_TYPE = NoteType.EKEYTECH;

    private static final Boolean DEFAULT_BOOL_VALUE = false;
    private static final Boolean UPDATED_BOOL_VALUE = true;

    private static final Integer DEFAULT_NUMBER_VALUE = 1;
    private static final Integer UPDATED_NUMBER_VALUE = 2;
    private static final String DEFAULT_NOTE_TEXT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NOTE_TEXT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private NoteRepository noteRepository;

    @Inject
    private NoteService noteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNoteMockMvc;

    private Note note;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoteResource noteResource = new NoteResource();
        ReflectionTestUtils.setField(noteResource, "noteService", noteService);
        this.restNoteMockMvc = MockMvcBuilders.standaloneSetup(noteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createEntity(EntityManager em) {
        Note note = new Note()
                .techId(DEFAULT_TECH_ID)
                .noteType(DEFAULT_NOTE_TYPE)
                .boolValue(DEFAULT_BOOL_VALUE)
                .numberValue(DEFAULT_NUMBER_VALUE)
                .noteText(DEFAULT_NOTE_TEXT);
        return note;
    }

    @Before
    public void initTest() {
        note = createEntity(em);
    }

    @Test
    @Transactional
    public void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // Create the Note

        restNoteMockMvc.perform(post("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(note)))
                .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = notes.get(notes.size() - 1);
        assertThat(testNote.getTechId()).isEqualTo(DEFAULT_TECH_ID);
        assertThat(testNote.getNoteType()).isEqualTo(DEFAULT_NOTE_TYPE);
        assertThat(testNote.isBoolValue()).isEqualTo(DEFAULT_BOOL_VALUE);
        assertThat(testNote.getNumberValue()).isEqualTo(DEFAULT_NUMBER_VALUE);
        assertThat(testNote.getNoteText()).isEqualTo(DEFAULT_NOTE_TEXT);
    }

    @Test
    @Transactional
    public void checkTechIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setTechId(null);

        // Create the Note, which fails.

        restNoteMockMvc.perform(post("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(note)))
                .andExpect(status().isBadRequest());

        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setNoteType(null);

        // Create the Note, which fails.

        restNoteMockMvc.perform(post("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(note)))
                .andExpect(status().isBadRequest());

        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the notes
        restNoteMockMvc.perform(get("/api/notes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId().intValue())))
                .andExpect(jsonPath("$.[*].techId").value(hasItem(DEFAULT_TECH_ID.intValue())))
                .andExpect(jsonPath("$.[*].noteType").value(hasItem(DEFAULT_NOTE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].boolValue").value(hasItem(DEFAULT_BOOL_VALUE.booleanValue())))
                .andExpect(jsonPath("$.[*].numberValue").value(hasItem(DEFAULT_NUMBER_VALUE)))
                .andExpect(jsonPath("$.[*].noteText").value(hasItem(DEFAULT_NOTE_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", note.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(note.getId().intValue()))
            .andExpect(jsonPath("$.techId").value(DEFAULT_TECH_ID.intValue()))
            .andExpect(jsonPath("$.noteType").value(DEFAULT_NOTE_TYPE.toString()))
            .andExpect(jsonPath("$.boolValue").value(DEFAULT_BOOL_VALUE.booleanValue()))
            .andExpect(jsonPath("$.numberValue").value(DEFAULT_NUMBER_VALUE))
            .andExpect(jsonPath("$.noteText").value(DEFAULT_NOTE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNote() throws Exception {
        // Initialize the database
        noteService.save(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        Note updatedNote = noteRepository.findOne(note.getId());
        updatedNote
                .techId(UPDATED_TECH_ID)
                .noteType(UPDATED_NOTE_TYPE)
                .boolValue(UPDATED_BOOL_VALUE)
                .numberValue(UPDATED_NUMBER_VALUE)
                .noteText(UPDATED_NOTE_TEXT);

        restNoteMockMvc.perform(put("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNote)))
                .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeUpdate);
        Note testNote = notes.get(notes.size() - 1);
        assertThat(testNote.getTechId()).isEqualTo(UPDATED_TECH_ID);
        assertThat(testNote.getNoteType()).isEqualTo(UPDATED_NOTE_TYPE);
        assertThat(testNote.isBoolValue()).isEqualTo(UPDATED_BOOL_VALUE);
        assertThat(testNote.getNumberValue()).isEqualTo(UPDATED_NUMBER_VALUE);
        assertThat(testNote.getNoteText()).isEqualTo(UPDATED_NOTE_TEXT);
    }

    @Test
    @Transactional
    public void deleteNote() throws Exception {
        // Initialize the database
        noteService.save(note);

        int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Get the note
        restNoteMockMvc.perform(delete("/api/notes/{id}", note.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
