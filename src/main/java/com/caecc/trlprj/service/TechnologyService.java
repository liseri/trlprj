package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.repository.TechnologyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Technology.
 */
@Service
@Transactional
public class TechnologyService {

    private final Logger log = LoggerFactory.getLogger(TechnologyService.class);
    
    @Inject
    private TechnologyRepository technologyRepository;

    /**
     * Save a technology.
     *
     * @param technology the entity to save
     * @return the persisted entity
     */
    public Technology save(Technology technology) {
        log.debug("Request to save Technology : {}", technology);
        Technology result = technologyRepository.save(technology);
        return result;
    }

    /**
     *  Get all the technologies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Technology> findAll(Pageable pageable) {
        log.debug("Request to get all Technologies");
        Page<Technology> result = technologyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one technology by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Technology findOne(Long id) {
        log.debug("Request to get Technology : {}", id);
        Technology technology = technologyRepository.findOne(id);
        return technology;
    }

    /**
     *  Delete the  technology by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Technology : {}", id);
        technologyRepository.delete(id);
    }
}
