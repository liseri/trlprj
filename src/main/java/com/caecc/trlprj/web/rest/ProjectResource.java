package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.service.UserService;
import com.caecc.trlprj.web.rest.vm.ProjectVM;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import com.codahale.metrics.annotation.Timed;
import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.service.ProjectService;
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
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private ProjectService projectService;

    @Inject
    private UserService userService;
    //region 项目增改删
    /**
     * POST  /projects : Create a new project.
     *
     * @param projectVM the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new project, or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectVM projectVM) throws URISyntaxException {
        log.debug("REST request to save Project : {}", projectVM);
        if (projectVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("project", "idexists", "A new project cannot already have an ID")).body(null);
        }
        Project result = projectService.createPrj(projectVM);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param projectVM the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated project,
     * or with status 400 (Bad Request) if the project is not valid,
     * or with status 500 (Internal Server Error) if the project couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> updateProject(@Valid @RequestBody ProjectVM projectVM) throws URISyntaxException {
        log.debug("REST request to update Project : {}", projectVM);
        if (projectVM.getId() == null) {
            return createProject(projectVM);
        }
        Project result = projectService.updatePrj(projectVM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project", projectVM.getId().toString()))
            .body(result);
    }
    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project", id.toString())).build();
    }
    //endregion

    //region 项目查询
    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Project>> getAllProjects(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Projects");
        Page<Project> page = projectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectService.findOne(id);
        return Optional.ofNullable(project)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //endregion

    //region 项目状态操作
    @RequestMapping(value = "/projects/{id}/start",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> startPrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.start(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "started", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/pause",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> pausePrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.pause(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "paused", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/complete",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> completePrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.complete(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "completed", id.toString())).build();
    }
    //endregion

    //region 项目相关人员添加操作
    @RequestMapping(value = "/projects/{id}/addtrler/{trlerLogin}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> addTrler(@PathVariable Long id, @PathVariable String trlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(trlerLogin).orElse(null);
        projectService.addTrler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addTrlered", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/rmtrler/{trlerLogin}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> removeTrler(@PathVariable Long id, @PathVariable String trlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(trlerLogin).orElse(null);
        projectService.removeTrler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "rmTrlered", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/addevler/{evlerLogin}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> addEvler(@PathVariable Long id, @PathVariable String evlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(evlerLogin).orElse(null);
        projectService.addEvler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addEvlered", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/rmevler/{evlerLogin}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> removeEvler(@PathVariable Long id, @PathVariable String evlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(evlerLogin).orElse(null);
        projectService.removeEvler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "rmEvlered", id.toString())).build();
    }
    //endregion

    //region 技术树操作
    @RequestMapping(value = "/projects/{id}/addroottech",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> addRootTech(@PathVariable Long id, @Valid @RequestBody TechnologyVM technologyVM) {
        Project project = projectService.findOne(id);
        projectService.addTech(project, null, technologyVM);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addRootTeched", id.toString())).build();
    }
    @RequestMapping(value = "/projects/{id}/rmroottech",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> removeRootTech(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        Technology rootTech = project.getRootTech();
        technologyRepository.delete(rootTech);
        project.rootTech(null);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "rmRootTeched", id.toString())).build();
    }
    //endregion
}
