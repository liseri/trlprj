package com.caecc.trlprj.web.rest;

import com.caecc.trlprj.domain.Authority;
import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.service.KeyTechService;
import com.caecc.trlprj.service.ProjectService;
import com.caecc.trlprj.service.UserService;
import com.caecc.trlprj.web.rest.util.HeaderUtil;
import com.caecc.trlprj.web.rest.util.PaginationUtil;
import com.caecc.trlprj.web.rest.vm.KeyTechVM;
import com.caecc.trlprj.web.rest.vm.ProjectVM;
import com.caecc.trlprj.web.rest.vm.Technology2VM;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import com.codahale.metrics.annotation.Timed;
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
public class KeyTechResource {

    private final Logger log = LoggerFactory.getLogger(KeyTechResource.class);

    @Inject
    private KeyTechService keyTechService;

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private ProjectService projectService;

    @Inject
    private UserService userService;


//    /**
//     * 关键技术的数据
//     * @param techId
//     * @return
//     */
//    @PostMapping(value = "/keytech/{techId}/type/{type}")
//    @Timed
//    public ResponseEntity<Void> setKeyTech(@PathVariable Long techId, @PathVariable KeyTechValueType type, @RequestBody KeyTechVM keyTechVM) {
//        log.debug("REST request to getKeyTech : {}", techId);
//        Technology technology = technologyRepository.findOne(techId);
//        User user = userService.getUserWithAuthorities();
//        //如果是创建人自己请求
//        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.USER)) &&
//            technology.getCreator().getLogin().equalsIgnoreCase(user.getLogin()))
//            return ResponseEntity.ok(keyTechService.getKeyTech(technology, user.getLogin(), type));
//        else if ((user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.TRL)) && technology.getPrj().getTrlers().contains(user)) ||
//            (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.EVL)) && technology.getPrj().getTrlers().contains(user)))
//            return ResponseEntity.ok(keyTechService.getKeyTech(technology, user.getLogin(), type));
//        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN))) {
//            String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
//            String userName = userInfos.length>0?userInfos[0]:"";
//            String branch = userInfos.length>1?userInfos[1]:"";
//            User relatedUser = userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).get();
//            if (relatedUser!= null)
//                return ResponseEntity.ok(keyTechService.getKeyTech(technology, relatedUser.getLogin(), type));
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    /**
     * 关键技术的数据
     * @param techId
     * @return
     */
    @GetMapping(value = "/keytech/{techId}/type/{type}/user/{userFullName}")
    @Timed
    public ResponseEntity<KeyTechVM> getKeyTech(@PathVariable Long techId, @PathVariable KeyTechValueType type, @PathVariable String userFullName) {
        log.debug("REST request to getKeyTech : {}", techId);
        Technology technology = technologyRepository.findOne(techId);
        User user = userService.getUserWithAuthorities();
        //如果是创建人自己请求
        if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.USER)) &&
            technology.getCreator().getLogin().equalsIgnoreCase(user.getLogin()))
            return ResponseEntity.ok(keyTechService.getKeyTech(technology, user.getLogin(), type));
        else if ((user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.TRL)) && technology.getPrj().getTrlers().contains(user)) ||
                  (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.EVL)) && technology.getPrj().getTrlers().contains(user)))
            return ResponseEntity.ok(keyTechService.getKeyTech(technology, user.getLogin(), type));
        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN))) {
            String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
            String userName = userInfos.length>0?userInfos[0]:"";
            String branch = userInfos.length>1?userInfos[1]:"";
            User relatedUser = userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).orElse(null);
            if (relatedUser!= null)
                return ResponseEntity.ok(keyTechService.getKeyTech(technology, relatedUser.getLogin(), type));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
