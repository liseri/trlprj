package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Brahch;
import com.caecc.trlprj.repository.BrahchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Brahch.
 */
@Service
@Transactional
public class BrahchService {

    private final Logger log = LoggerFactory.getLogger(BrahchService.class);
    
    @Inject
    private BrahchRepository brahchRepository;

    /**
     * Save a brahch.
     *
     * @param brahch the entity to save
     * @return the persisted entity
     */
    public Brahch save(Brahch brahch) {
        log.debug("Request to save Brahch : {}", brahch);
        Brahch result = brahchRepository.save(brahch);
        return result;
    }

    /**
     *  Get all the brahches.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Brahch> findAll() {
        log.debug("Request to get all Brahches");
        List<Brahch> result = brahchRepository.findAll();

        return result;
    }

    /**
     *  Get one brahch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Brahch findOne(Long id) {
        log.debug("Request to get Brahch : {}", id);
        Brahch brahch = brahchRepository.findOne(id);
        return brahch;
    }

    /**
     *  Delete the  brahch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Brahch : {}", id);
        brahchRepository.delete(id);
    }
}
