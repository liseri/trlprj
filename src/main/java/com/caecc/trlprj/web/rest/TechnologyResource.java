package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import com.codahale.metrics.annotation.Timed;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.service.TechnologyService;
import com.caecc.trlprj.web.rest.util.HeaderUtil;
import com.caecc.trlprj.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Technology.
 */
@RestController
@RequestMapping("/api")
public class TechnologyResource {

    private final Logger log = LoggerFactory.getLogger(TechnologyResource.class);
    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private TechnologyRepository technologyRepository;
    @Inject
    private TechnologyService technologyService;

    /**
     * POST  /technologies : Create a new technology.
     *
     * @param technologyVM the technology to create
     * @return the ResponseEntity with status 201 (Created) and with body the new technology, or with status 400 (Bad Request) if the technology has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/technologies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Technology> createTechnology(@Valid @RequestBody TechnologyVM technologyVM) throws URISyntaxException {
        log.debug("REST request to save Technology : {}", technologyVM);
        if (technologyVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("technology", "idexists", "A new technology cannot already have an ID")).body(null);
        }
        Project project = projectRepository.findOne(technologyVM.getPrjId());
        Technology parentTech = technologyRepository.findOne(technologyVM.getParentTechId());
        Technology result = technologyService.createTech(project, parentTech, technologyVM);
        return ResponseEntity.created(new URI("/api/technologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("technology", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /technologies : Updates an existing technology.
     *
     * @param technologyVM the technology to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated technology,
     * or with status 400 (Bad Request) if the technology is not valid,
     * or with status 500 (Internal Server Error) if the technology couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/technologies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Technology> updateTechnology(@Valid @RequestBody TechnologyVM technologyVM) throws URISyntaxException {
        log.debug("REST request to update Technology : {}", technologyVM);
        if (technologyVM.getId() == null) {
            return createTechnology(technologyVM);
        }
        Technology technology = technologyRepository.findOne(technologyVM.getId());
        Technology result = technologyService.updateTech(technology, technologyVM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("technology", technologyVM.getId().toString()))
            .body(result);
    }

    /**
     * GET  /technologies : get all the technologies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of technologies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/technologies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Technology>> getAllTechnologies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Technologies");
        Page<Technology> page = technologyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/technologies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /technologies/:id : get the "id" technology.
     *
     * @param id the id of the technology to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the technology, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/technologies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Technology> getTechnology(@PathVariable Long id) {
        log.debug("REST request to get Technology : {}", id);
        Technology technology = technologyService.findOne(id);
        return Optional.ofNullable(technology)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /technologies/:id : delete the "id" technology.
     *
     * @param id the id of the technology to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/technologies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        log.debug("REST request to delete Technology : {}", id);
        technologyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("technology", id.toString())).build();
    }

}
