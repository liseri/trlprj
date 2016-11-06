package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.service.UserService;
import com.caecc.trlprj.web.rest.vm.ProjectVM;
import com.caecc.trlprj.web.rest.vm.Technology2VM;
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
import java.util.stream.Collectors;

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
    @GetMapping(value = "/projects/{id}/start")
    @Timed
    public ResponseEntity<Void> startPrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.start(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "started", id.toString())).build();
    }
    @GetMapping(value = "/projects/{id}/pause")
    @Timed
    public ResponseEntity<Void> pausePrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.pause(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "paused", id.toString())).build();
    }
    @GetMapping(value = "/projects/{id}/complete")
    @Timed
    public ResponseEntity<Void> completePrj(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        projectService.complete(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "completed", id.toString())).build();
    }
    //endregion

    //region 项目相关人员添加操作
    @PostMapping(value = "/projects/{id}/trler/{trlerLogin}")
    @Timed
    public ResponseEntity<User> addTrler(@PathVariable Long id, @PathVariable String trlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(trlerLogin).orElse(null);
        projectService.addTrler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addTrlered", project.getName())).body(trler);
    }
    @DeleteMapping(value = "/projects/{id}/trler/{trlerLogin}")
    @Timed
    public ResponseEntity<User> removeTrler(@PathVariable Long id, @PathVariable String trlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(trlerLogin).orElse(null);
        projectService.removeTrler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "rmTrlered", project.getName())).body(trler);
    }
    @PostMapping(value = "/projects/{id}/evler/{evlerLogin}")
    @Timed
    public ResponseEntity<User> addEvler(@PathVariable Long id, @PathVariable String evlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(evlerLogin).orElse(null);
        projectService.addEvler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addEvlered", project.getName())).body(trler);
    }
    @DeleteMapping(value = "/projects/{id}/evler/{evlerLogin}")
    @Timed
    public ResponseEntity<User> removeEvler(@PathVariable Long id, @PathVariable String evlerLogin) {
        Project project = projectService.findOne(id);
        User trler = userService.getUserWithAuthoritiesByLogin(evlerLogin).orElse(null);
        projectService.removeEvler(project, trler);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "rmEvlered", project.getName())).body(trler);
    }
    //endregion

    //region 技术树操作
    @GetMapping(value = "/myprj/{id}/tech/{techId}")
    @Timed
    public ResponseEntity<Technology> getTech(@PathVariable Long id, @PathVariable Long techId) {
        log.debug("REST request to get Technology : {}", id);
        Technology technology = technologyRepository.findOne(id);
        return Optional.ofNullable(technology)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/myprj/{id}/tech")
    @Timed
    public ResponseEntity<Technology2VM> addTech(@PathVariable Long id, @Valid @RequestBody TechnologyVM technologyVM) {
        Project project = projectService.findOne(id);
        Technology technology = projectService.addTech(project, technologyVM);
        Technology2VM technology2VM = Technology2VM.fromTechForCreatorOrParentCreator(technology, true, false, true);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addTeched", id.toString())).body(technology2VM);
    }
    @PutMapping(value = "/myprj/{id}/tech")
    @Timed
    public ResponseEntity<Technology2VM> updateTech(@PathVariable Long id, @Valid @RequestBody TechnologyVM technologyVM) {
        Project project = projectService.findOne(id);
        Technology technology = projectService.updateTech(technologyVM);
        Technology2VM technology2VM = Technology2VM.fromTechForCreatorOrParentCreator(technology, true, false, true);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "updatedTeched", id.toString())).body(technology2VM);
    }
    @DeleteMapping(value = "/myprj/{id}/tech/{techId}")
    @Timed
    public ResponseEntity<Void> deleteTech(@PathVariable Long id, @PathVariable Long techId) {
        Project project = projectService.findOne(id);
        projectService.deleteTech(project, techId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "deleteTeched", id.toString())).build();
    }
    @PostMapping(value = "/myprj/{id}/tech/{techId}/key/{isKey}")
    @Timed
    public ResponseEntity<Technology2VM> changeKey(@PathVariable Long id, @PathVariable Long techId, @PathVariable Boolean isKey) {
        Project project = projectService.findOne(id);
        Technology technology = technologyRepository.findOne(techId);
        technology = projectService.setKey(technology, isKey);
        Technology2VM technology2VM = Technology2VM.fromTechForCreatorOrParentCreator(technology, true, false, true);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", isKey?"settedKey":"settedNotKey", id.toString())).body(technology2VM);
    }
    @PostMapping(value = "/myprj/{id}/tech/{techId}/subcreator/{userLogin}")
    @Timed
    public ResponseEntity<List<String>> addSubcreator(@PathVariable Long id, @PathVariable Long techId, @PathVariable String userLogin) {
        Project project = projectService.findOne(id);
        Technology technology = technologyRepository.findOne(techId);
        User user = userService.getUserWithAuthoritiesByLogin(userLogin).get();
        //不能添加自己为子结点创建人
        if (user.getLogin().equals(userService.getUserWithAuthorities().getLogin()))
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("project", "subCreatorcannotme", "不能添加自己为子结点创建人")).body(null);
        projectService.addSubCreator(project, techId, user);
        technology = technologyRepository.findOne(techId);
        List<String> subcreators = technology.getSubCreators()
            .stream()
            .map(creator->creator.getFullName())
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "addedSubcreator", techId.toString())).body(subcreators);
    }
    @DeleteMapping(value = "/myprj/{id}/tech/{techId}/subcreator/{userFullName}")
    @Timed
    public ResponseEntity<List<String>> removeSubcreator(@PathVariable Long id, @PathVariable Long techId, @PathVariable String userFullName) {
        Project project = projectService.findOne(id);
        Technology technology = technologyRepository.findOne(techId);
        String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
        String userName = userInfos.length>0?userInfos[0]:"";
        String branch = userInfos.length>1?userInfos[1]:"";
        User user = userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).get();
        projectService.removeSubCreator(project, techId, user);
        technology = technologyRepository.findOne(techId);
        List<String> subcreators = technology.getSubCreators()
            .stream()
            .map(creator->creator.getFullName())
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityOperationAlert("project", "removedSubcreator", techId.toString())).body(subcreators);
    }
    //endregion

    //region 我的项目

    /**
     * 获得用户的可用项目
     * @return
     */
    @GetMapping(value = "/myprj")
    @Timed
    public ResponseEntity<List<ProjectVM>> getMyPrj() {
        List<ProjectVM> prjs = projectService.getAvaliblePrj();
        return ResponseEntity.ok(prjs);
    }

    /**
     * 获得用户可用的技术树
     * @param id
     * @return
     */
    @GetMapping(value = "/myprj/{id}/techtree")
    @Timed
    public ResponseEntity<Technology2VM> getMyTechTree(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        Technology2VM technology2VM = projectService.getAvalibleTech(project);
        return ResponseEntity.ok().body(technology2VM);
    }
    //endregion
}
