package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Note;
import com.caecc.trlprj.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Note.
 */
@Service
@Transactional
public class NoteService {

    private final Logger log = LoggerFactory.getLogger(NoteService.class);
    
    @Inject
    private NoteRepository noteRepository;

    /**
     * Save a note.
     *
     * @param note the entity to save
     * @return the persisted entity
     */
    public Note save(Note note) {
        log.debug("Request to save Note : {}", note);
        Note result = noteRepository.save(note);
        return result;
    }

    /**
     *  Get all the notes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Note> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        Page<Note> result = noteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one note by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Note findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        Note note = noteRepository.findOne(id);
        return note;
    }

    /**
     *  Delete the  note by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.delete(id);
    }
}
