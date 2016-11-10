package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.domain.*;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.repository.KeyTechRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.service.KeyTechService;
import com.caecc.trlprj.service.ProjectService;
import com.caecc.trlprj.service.UserService;
import com.caecc.trlprj.web.rest.util.HeaderUtil;
import com.caecc.trlprj.web.rest.vm.KeyTechVM;
import com.caecc.trlprj.web.rest.vm.KeyTechsVM;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class KeyTechResource {

    private final Logger log = LoggerFactory.getLogger(KeyTechResource.class);

    @Inject
    private KeyTechRepository keyTechRepository;

    @Inject
    private KeyTechService keyTechService;

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private ProjectService projectService;

    @Inject
    private UserService userService;

    @PostMapping(value = "/keytech/{techId}/type/{type}")
    @Timed
    public ResponseEntity<KeyTech> createKeyTech(@PathVariable Long techId,  @PathVariable KeyTechValueType type,
                                                @Valid @RequestBody KeyTechVM keyTechvVM) throws URISyntaxException {
        log.debug("REST request to createKeyTech : {}", keyTechvVM);
        if (keyTechvVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("keytech", "idexists", "A new keytech cannot already have an ID")).body(null);
        }
        Technology technology = technologyRepository.findOne(techId);
        KeyTech result = keyTechService.saveKeyTech(technology, keyTechvVM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("keytech", result.getId().toString()))
            .body(result);
    }


    @PutMapping(value = "/keytech/{techId}/type/{type}")
    @Timed
    public ResponseEntity<KeyTech> updateKeyTech(@PathVariable Long techId,  @PathVariable KeyTechValueType type,
                                               @Valid @RequestBody KeyTechVM keyTechvVM) throws URISyntaxException {
        log.debug("REST request to updateBranch : {}", keyTechvVM);
        if (keyTechvVM.getId() == null) {
            return createKeyTech(techId, type, keyTechvVM);
        }
        Technology technology = technologyRepository.findOne(techId);
        KeyTech result = keyTechService.saveKeyTech(technology, keyTechvVM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("keytech", result.getId().toString()))
            .body(result);
    }

    /**
     * 关键技术的数据
     * @param techId
     * @return
     */
    @GetMapping(value = "/keytech/{techId}/type/{type}")
    @Timed
    public ResponseEntity<KeyTechsVM> getKeyTech(@PathVariable Long techId, @PathVariable KeyTechValueType type, Pageable pageable) {
        log.debug("REST request to getKeyTech : {}", techId);
        Technology technology = technologyRepository.findOne(techId);
        User user = userService.getUserWithAuthorities();
        //如果是创建人自己请求
//        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.USER)) &&
//            technology.getCreator().getLogin().equalsIgnoreCase(user.getLogin()))
//            return ResponseEntity.ok(keyTechService.getKeyTech(technology, type));
//        else if ((user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.TRL)) && technology.getPrj().getTrlers().contains(user)) ||
//                  (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.EVL)) && technology.getPrj().getTrlers().contains(user)))
//            return ResponseEntity.ok(keyTechService.getKeyTech(technology, user.getLogin(), type));
//        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN))) {
//            String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
//            String userName = userInfos.length>0?userInfos[0]:"";
//            String branch = userInfos.length>1?userInfos[1]:"";
//            User relatedUser = userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).orElse(null);
//            if (relatedUser!= null)
//                return ResponseEntity.ok(keyTechService.getKeyTech(technology, relatedUser.getLogin(), type));
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(keyTechService.getKeyTech(technology, type, pageable));
    }

}
