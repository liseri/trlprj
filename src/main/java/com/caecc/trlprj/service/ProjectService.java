package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Authority;
import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.PrjStatus;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.repository.UserRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.web.rest.vm.ProjectVM;
import com.caecc.trlprj.web.rest.vm.Technology2VM;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserRepository userRepository;

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
     * Delete the  project by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        Project project = projectRepository.findOne(id);
        project.rootTech(null);
//        project.getTeches().stream().forEach(tech -> technologyRepository.delete(tech));
        technologyRepository.findByProjectId(id).stream().forEach(tech -> technologyRepository.delete(tech));
        projectRepository.delete(id);
    }
    //endregion

    //region  项目查询

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
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
     *
     * @param project
     */
    public void start(Project project) {
        if (project.getStatu() == PrjStatus.STARTED)
            return;
        project.statu(PrjStatus.STARTED).startTime(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        projectRepository.save(project);
    }

    /**
     * 暂停
     *
     * @param project
     */
    public void pause(Project project) {
        if (project.getStatu() == PrjStatus.PAUSED)
            return;
        project.statu(PrjStatus.PAUSED);
        projectRepository.save(project);
    }

    /**
     * 完成
     *
     * @param project
     */
    public void complete(Project project) {
        if (project.getStatu() == PrjStatus.COMPLETED)
            return;
        project.statu(PrjStatus.COMPLETED).completeTime(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        projectRepository.save(project);
    }
    //endregion

    //region 项目相关人员添加操作

    /**
     * 添加TRL技术人员
     *
     * @param project
     * @param trler
     */
    public void addTrler(Project project, User trler) {
        project.addTrlers(trler);
        projectRepository.save(project);
    }

    /**
     * 删除TRL技术人员
     *
     * @param project
     * @param trler
     */
    public void removeTrler(Project project, User trler) {
        project.removeTrlers(trler);
        projectRepository.save(project);
    }

    /**
     * 添加评审专家
     *
     * @param project
     * @param evler
     */
    public void addEvler(Project project, User evler) {
        project.addEvlers(evler);
        projectRepository.save(project);
    }

    /**
     * 删除评审专家
     *
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
     * 获取可用技术
     * @param project
     * @return
     */
    public Technology2VM getAvalibleTech(Project project) {
        Technology rootTech = project.getRootTech();
        if (rootTech == null)
            return null;
        //设置技术树根结点
        Technology2VM technology2VM = new Technology2VM();
        User user = userService.getUserWithAuthorities();
        getTech2VM(technology2VM, rootTech, user, rootTech.getCreator().getLogin().equals(user.getLogin()));
        return technology2VM.getSubTechs().get(0);
    }

    /**
     * 获取可用技术
     * @param technology2VM
     * @param tech
     * @param user
     * @param userAsParentCreator
     */
    private void getTech2VM(Technology2VM technology2VM, Technology tech, User user, boolean userAsParentCreator) {
        Technology2VM currentTech2VM = dealTech(technology2VM, tech, user, userAsParentCreator);
        tech.getSubTeches().stream().forEach(subTech-> {
            getTech2VM(currentTech2VM, subTech, user, tech.getCreator().getLogin().equals(user.getLogin()));
        });

    }
    private Technology2VM dealTech(Technology2VM technology2VM, Technology tech, User user, boolean userAsParentCreator) {
        boolean asCreator = tech.getCreator().getLogin().equals(user.getLogin());
        boolean asSubCreator = false;
        Technology2VM nextTech2VM = null;
        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN))) {
            nextTech2VM = Technology2VM.fromTechForCreatorOrParentCreator(tech, asCreator, true);
        }
        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.TRL))
            || user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.EVL))) {
            nextTech2VM = Technology2VM.fromTechForBigViewer(tech);
        }
        //普通研发人员
        else {
            //是结点拥有者或创建者
            if (userAsParentCreator == true || asCreator == true)
                nextTech2VM = Technology2VM.fromTechForCreatorOrParentCreator(tech, asCreator, userAsParentCreator);
            //非结点创建者和拥有者，则具有最普通的查看权限
            else nextTech2VM = Technology2VM.fromTechForBigViewer(tech);

        }
        technology2VM.getSubTechs().add(nextTech2VM);
        return nextTech2VM;

    }

    /**
     * 添加技术结点
     *
     * @param project
     * @param technologyVM
     */
    public Project addTech(Project project, TechnologyVM technologyVM) {
        Technology parentTech = null;
        Technology technology = null;
        //取父结点
        if (technologyVM.getParentTechId() != null && technologyVM.getParentTechId() > 0)
            parentTech = technologyRepository.findOne(technologyVM.getParentTechId());
        //取本结点
        technology = technologyVM.toTechnology(parentTech, userService.getUserWithAuthorities());
        //维护本结点的树排序信息
        //如果是根结点就添加根结点
        if (parentTech == null)
            technology.setOrderId("1");
        else {
            //检测该结点是否有子结点
            //如果没有则创建第一个子结点
            if (parentTech.getSubTeches() == null || parentTech.getSubTeches().size() <= 0)
                technology.setOrderId(parentTech.getOrderId() + ".1");
                //如果不是第一个子结点
            else {
                //父结点的最后一个子结点的排序序号
                String lastOrderId = parentTech.getSubTeches().get(parentTech.getSubTeches().size()-1).getOrderId();
                technology.setOrderId(getNextOrderId(lastOrderId));
            }
        }
        //保存本结点
        technologyRepository.save(technology);
        //维护项目等其它信息
//        project.addTech(technology);
        if (parentTech == null)
            project.rootTech(technology);
        else parentTech.addSubTech(technology);
        projectRepository.save(project);
        return project;
    }

    private String getNextOrderId(String lastOrderId) {
        String[] orderArray = lastOrderId.split("\\.");
        orderArray[orderArray.length - 1] = "" + (Integer.parseInt(orderArray[orderArray.length - 1]) + 1);
        return String.join(".", orderArray);
    }

    /**
     * 修改技术结点信息
     * @param technologyVM
     * @return
     */
    public Project updateTech(TechnologyVM technologyVM) {
        Technology technology = technologyRepository.findOne(technologyVM.getId());
        technology.updateFrom(technologyVM);
        Long parentTechId = technologyVM.getParentTechId();
        if (parentTechId != null && parentTechId > 0 && technology.getParentTech().getId() != parentTechId)
            technology.parentTech(technologyRepository.findOne(parentTechId));
        technologyRepository.save(technology);
        return technology.getPrj();
    }

    /**
     * 删除技术结点
     *
     * @param project
     * @param techId
     */
    public void deleteTech(Project project, Long techId) {
        Technology technology = null;
        technology = technologyRepository.findOne(techId);
        //如果是根结点
        if (project.getRootTech() != null && project.getRootTech().getId() == techId) {
            project.rootTech(null);
//            project.removeTech(technology);
            projectRepository.save(project);
        }
        //如果不是根结点
        //更新其父结点子结点
        if (technology.getParentTech() != null)
            technology.getParentTech().removeSubTech(technology);
        //删除该根点及其子结点
        technology.getSubTeches().forEach(subTech -> technologyRepository.delete(subTech));
        technologyRepository.delete(technology);
    }

    /**
     * 为结点添加子结点创建人
     *
     * @param project
     * @param techId
     * @param subCreator
     */
    public void addSubCreator(Project project, Long techId, User subCreator) {
//        Technology technology = project.getTeches().stream().filter(item -> item.getId() == techId).findFirst().orElse(null);
        Technology technology = technologyRepository.findOne(techId);
        technology.addSubCreator(subCreator);
        technologyRepository.save(technology);
    }

    /**
     * 为结点删除子结点创建人
     *
     * @param project
     * @param techId
     * @param subCreator
     */
    public void removeSubCreator(Project project, Long techId, User subCreator) {
//        Technology technology = project.getTeches().stream().filter(item -> item.getId() == techId).findFirst().orElse(null);
        Technology technology = technologyRepository.findOne(techId);
        technology.removeSubCreator(subCreator);
        technologyRepository.save(technology);
    }
    //endregion

    //region 可用项目查询

    /**
     * 获得用户的可用项目
     * 1. 管理员不走这个接口
     * 2. 有四种：项目办管理员，TRL专业人员，评审专家，普通研发人员
     * 3. 暂时假设这四种人员角色不会重叠
     *
     * @return
     */
    public List<ProjectVM> getAvaliblePrj() {
        User user = userService.getUserWithAuthorities();
        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN)))
            return getAvaliblePrjOfAdmin(user);
        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.TRL)))
            return getAvaliblePrjOfTrler(user);
        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.EVL)))
            return getAvaliblePrjOfEvler(user);
        else return getAvaliblePrjOfUser(user);
    }

    /**
     * 获取项目办管理人员可用项目
     *
     * @param admin
     * @return
     */
    private List<ProjectVM> getAvaliblePrjOfAdmin(User admin) {
        return projectRepository.findAllWithEagerRelationships().stream()
            .filter(prj -> prj.getCreator().getLogin() == admin.getLogin())
            .map(prj -> new ProjectVM().from(prj))
            .collect(Collectors.toList());
    }

    /**
     * 获取TRL专业人员可用项目
     *
     * @param trler
     * @return
     */
    private List<ProjectVM> getAvaliblePrjOfTrler(User trler) {
        return projectRepository.findAllWithEagerRelationships().stream()
            .filter(prj -> prj.getStatu() == PrjStatus.STARTED && prj.getEvlers().contains(trler))
            .map(new ProjectVM()::from)
            .collect(Collectors.toList());
    }

    /**
     * 获取评审专家可用项目
     *
     * @param evler
     * @return
     */
    private List<ProjectVM> getAvaliblePrjOfEvler(User evler) {
        return projectRepository.findAllWithEagerRelationships().stream()
            .filter(prj -> prj.getStatu() == PrjStatus.STARTED && prj.getEvlers().contains(evler))
            .map(new ProjectVM()::from)
            .collect(Collectors.toList());
    }

    /**
     * 获取普通研发人员可用项目
     *
     * @param user
     * @return
     */
    private List<ProjectVM> getAvaliblePrjOfUser(User user) {
        List<ProjectVM> prjs = Collections.emptyList();
        technologyRepository.findAllWithEagerRelationships().stream()
            .filter(tech -> tech.getPrj().getStatu() == PrjStatus.STARTED && (tech.getCreator().getLogin() == user.getLogin() || tech.getSubCreators().contains(user)))
            .forEach(tech -> prjs.add(new ProjectVM().from(tech.getPrj())));
        return prjs;
    }

    //endregion
}
