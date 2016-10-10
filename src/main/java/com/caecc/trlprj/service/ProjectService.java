package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.PrjStatus;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.web.rest.vm.ProjectVM;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private UserService userService;

    //region 项目增改删
    /**
     * 创建新项目.
     *
     * @param projectVM the entity to save
     * @return the persisted entity
     */
    public Project createPrj(ProjectVM projectVM) {
        log.debug("Request to save Project : {}", projectVM);
        Project project = new Project()
            .name(projectVM.getName())
            .descript1(projectVM.getDescript1())
            .descript2(projectVM.getDescript2())
            .descript3(projectVM.getDescript3())
            .descript4(projectVM.getDescript4())
            .statu(PrjStatus.CREATED)                        //状态
            .creator(userService.getUserWithAuthorities()); //创建人
        Project result = projectRepository.save(project);
        return result;
    }
    /**
     * 修改项目信息.
     *
     * @param projectVM the entity to save
     * @return the persisted entity
     */
    public Project updatePrj(ProjectVM projectVM) {
        log.debug("Request to save Project : {}", projectVM);
        Project project = projectRepository.findOne(projectVM.getId());
        project.name(projectVM.getName())
            .descript1(projectVM.getDescript1())
            .descript2(projectVM.getDescript2())
            .descript3(projectVM.getDescript3())
            .descript4(projectVM.getDescript4())
            .statu(PrjStatus.CREATED)                        //状态
            .creator(userService.getUserWithAuthorities()); //创建人
        Project result = projectRepository.save(project);
        return result;
    }
    /**
     *  Delete the  project by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
    //endregion

    //region  项目查询
    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one project by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Project findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOneWithEagerRelationships(id);
        return project;
    }
    //endregion

    //region 项目状态操作
    /**
     * 启动
     * @param project
     */
    public void start(Project project) {
        if (project.getStatu() == PrjStatus.STARTED)
            return;
        project.statu(PrjStatus.STARTED).startTime(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    }
    /**
     * 暂停
     * @param project
     */
    public void pause(Project project) {
        if (project.getStatu() == PrjStatus.PAUSED)
            return;
        project.statu(PrjStatus.PAUSED);
    }
    /**
     * 完成
     * @param project
     */
    public void complete(Project project) {
        if (project.getStatu() == PrjStatus.COMPLETED)
            return;
        project.statu(PrjStatus.PAUSED).completeTime(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    }
    //endregion

    //region 项目相关人员添加操作

    /**
     * 添加TRL技术人员
     * @param project
     * @param trler
     */
    public void addTrler(Project project, User trler) {
        project.addTrlers(trler);
        projectRepository.save(project);
    }
    /**
     * 删除TRL技术人员
     * @param project
     * @param trler
     */
    public void removeTrler(Project project, User trler) {
        project.removeTrlers(trler);
        projectRepository.save(project);
    }
    /**
     * 添加评审专家
     * @param project
     * @param evler
     */
    public void addEvler(Project project, User evler) {
        project.addEvlers(evler);
        projectRepository.save(project);
    }
    /**
     * 删除评审专家
     * @param project
     * @param evler
     */
    public void removeEvler(Project project, User evler) {
        project.removeEvlers(evler);
        projectRepository.save(project);
    }
    //endregion

    //region 技术树操作
    /**
     * 添加技术结点
     * @param project
     * @param technologyVM
     */
    public void addTech(Project project, Technology parentTech, TechnologyVM technologyVM) {
        Technology technology = technologyVM.toTechnology(parentTech, userService.getUserWithAuthorities());
        project.addTech(technology);
        if (parentTech != null)
            project.rootTech(technology);
        projectRepository.save(project);
    }
    /**
     * 删除技术结点
     * @param project
     * @param technologyVM
     */
    public void removeTech(Project project, Technology parentTech, TechnologyVM technologyVM) {
        Technology technology = technologyVM.toTechnology(parentTech, userService.getUserWithAuthorities());
        project.removeTech(technology);
        if (parentTech != null)
            project.rootTech(null);
        projectRepository.save(project);
    }
    /**
     * 为结点添加子结点创建人
     * @param project
     * @param techId
     * @param subCreator
     */
    public void addSubCreator(Project project, Long techId, User subCreator) {
        Technology technology = project.getTeches().stream().filter(item->item.getId() == techId).findFirst().orElse(null);
        technology.addSubCreator(subCreator);
        technologyRepository.save(technology);
    }
    /**
     * 为结点删除子结点创建人
     * @param project
     * @param techId
     * @param subCreator
     */
    public void removeSubCreator(Project project, Long techId, User subCreator) {
        Technology technology = project.getTeches().stream().filter(item->item.getId() == techId).findFirst().orElse(null);
        technology.removeSubCreator(subCreator);
        technologyRepository.save(technology);
    }
    //endregion

}
