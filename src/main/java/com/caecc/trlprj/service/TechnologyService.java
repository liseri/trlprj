package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
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

    @Inject
    private ProjectService projectService;

    //region 技术增改删
    /**
     * 创建技术结点.
     *
     * @param technologyVM the entity to save
     * @return the persisted entity
     */
    public Technology createTech(Project project, Technology parentTech, TechnologyVM technologyVM) {
        log.debug("Request to save Technology : {}", technologyVM);
        Technology technology = new Technology()
            .name(technologyVM.getName())
            .descript(technologyVM.getDescript());
        if (parentTech == null)
            projectService.addRootTech(project, technology);
        else projectService.addNormalTech(project, parentTech, technology);
//        Technology result = technologyRepository.save(technology);
        return technology;
    }
    /**
     * 更新技术结点.
     *
     * @param technologyVM the entity to save
     * @return the persisted entity
     */
    public Technology updateTech(Technology oldTech, TechnologyVM technologyVM) {
        log.debug("Request to save Technology : {}", technologyVM);
        oldTech.name(technologyVM.getName())
            .descript(technologyVM.getDescript());
        Technology result = technologyRepository.save(oldTech);
        return result;
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
    //endregion

    //region 技术查询
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
        Technology technology = technologyRepository.findOneWithEagerRelationships(id);
        return technology;
    }
    //endregion

    //region 技术其它业务操作

    //endregion

}
