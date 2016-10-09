package com.caecc.trlprj.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caecc.trlprj.domain.Brahch;
import com.caecc.trlprj.service.BrahchService;
import com.caecc.trlprj.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Brahch.
 */
@RestController
@RequestMapping("/api")
public class BrahchResource {

    private final Logger log = LoggerFactory.getLogger(BrahchResource.class);
        
    @Inject
    private BrahchService brahchService;

    /**
     * POST  /brahches : Create a new brahch.
     *
     * @param brahch the brahch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brahch, or with status 400 (Bad Request) if the brahch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/brahches",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brahch> createBrahch(@Valid @RequestBody Brahch brahch) throws URISyntaxException {
        log.debug("REST request to save Brahch : {}", brahch);
        if (brahch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("brahch", "idexists", "A new brahch cannot already have an ID")).body(null);
        }
        Brahch result = brahchService.save(brahch);
        return ResponseEntity.created(new URI("/api/brahches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("brahch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brahches : Updates an existing brahch.
     *
     * @param brahch the brahch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brahch,
     * or with status 400 (Bad Request) if the brahch is not valid,
     * or with status 500 (Internal Server Error) if the brahch couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/brahches",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brahch> updateBrahch(@Valid @RequestBody Brahch brahch) throws URISyntaxException {
        log.debug("REST request to update Brahch : {}", brahch);
        if (brahch.getId() == null) {
            return createBrahch(brahch);
        }
        Brahch result = brahchService.save(brahch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("brahch", brahch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brahches : get all the brahches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of brahches in body
     */
    @RequestMapping(value = "/brahches",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Brahch> getAllBrahches() {
        log.debug("REST request to get all Brahches");
        return brahchService.findAll();
    }

    /**
     * GET  /brahches/:id : get the "id" brahch.
     *
     * @param id the id of the brahch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brahch, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/brahches/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brahch> getBrahch(@PathVariable Long id) {
        log.debug("REST request to get Brahch : {}", id);
        Brahch brahch = brahchService.findOne(id);
        return Optional.ofNullable(brahch)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /brahches/:id : delete the "id" brahch.
     *
     * @param id the id of the brahch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/brahches/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBrahch(@PathVariable Long id) {
        log.debug("REST request to delete Brahch : {}", id);
        brahchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("brahch", id.toString())).build();
    }

}
