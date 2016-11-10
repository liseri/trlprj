package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.*;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.repository.KeyTechRepository;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.repository.UserRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.web.rest.vm.*;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class KeyTechService {

    private final Logger log = LoggerFactory.getLogger(KeyTechService.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private KeyTechRepository keyTechRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;


    public KeyTech saveKeyTech(Technology technology, KeyTechVM keyTechVM) {
        log.debug("Request to saveKeyTechOfCreator : {}", keyTechVM);
        User user = userService.getUserWithAuthorities();
        KeyTech keyTech = KeyTech.fromKeyTechVM(technology, keyTechVM);
        //FromUser
        keyTech.setFromUserFullname(user.getFullName());
        if (technology.getCreator().getLogin().equals(user.getLogin()))
            keyTech.setFromUserType(AuthoritiesConstants.USER);
        else if (technology.getPrj().getTrlers().contains(user))
            keyTech.setFromUserType(AuthoritiesConstants.TRL);
        else if (technology.getPrj().getEvlers().contains(user))
            keyTech.setFromUserType(AuthoritiesConstants.EVL);
        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN)))
            keyTech.setFromUserType(AuthoritiesConstants.ADMIN);
        else return null;
        //ToUser  如果ToId为空，则默认认为回复给创建人
        if (keyTechVM.getToId() == null || keyTechVM.getId() <0) {
            keyTech.setToUserFullname(technology.getCreator().getFullName());
            keyTech.setToUserType(AuthoritiesConstants.USER);
        }
        else {
            KeyTech toKeyTech = keyTechRepository.findOne(keyTechVM.getId());
            keyTech.setToUserFullname(toKeyTech.getFromUserFullname());
            keyTech.setToUserType(toKeyTech.getFromUserType());
        }
        return keyTechRepository.save(keyTech);
    }
    private User getUserFromFullName(String userFullName) {
        String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
        String userName = userInfos.length>0?userInfos[0]:"";
        String branch = userInfos.length>1?userInfos[1]:"";
        return userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).get();
    }
    public KeyTechsVM getKeyTech(Technology technology, KeyTechValueType keyValueType, Pageable pageable) {
        User user = userService.getUserWithAuthorities();
        boolean isAdmin = user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN));
        List<KeyTechValue> keyValus = keyTechRepository.findAllByTechIdAndKeyValueType(technology.getId(), keyValueType, pageable).getContent()
            .stream()
//            .filter(keyTech -> keyTech.getKeyValueType().equals(keyValueType))
            .map(keyTech -> {
                KeyTechValue keyTechValue = new KeyTechValue();
                keyTechValue.setFromUserType(keyTech.getFromUserType());
                keyTechValue.setToUserFullName(keyTech.getToUserFullname());
                keyTechValue.setToUserType(keyTech.getToUserType());
                keyTechValue.setValue(keyTech.getValue());
                keyTechValue.setValue2(keyTech.getValue2());
                keyTechValue.setNote(keyTech.getNote());
                if (isAdmin == true || keyTech.getFromUserFullname().equalsIgnoreCase(user.getFullName()))
                    keyTechValue.setFromUserFullName(keyTech.getFromUserFullname());
                else keyTechValue.setFromUserFullName("匿名");
                if (isAdmin == true || keyTech.getToUserFullname().equalsIgnoreCase(user.getFullName()))
                    keyTechValue.setToUserFullName(keyTech.getToUserFullname());
                else keyTechValue.setToUserFullName("匿名");
                return keyTechValue;
            })
            .collect(Collectors.toList());
        KeyTechsVM keyTechVM = new KeyTechsVM();
        keyTechVM.setTechId(technology.getId());
        keyTechVM.setKeyValueType(keyValueType);
        keyTechVM.setKeyValues(keyValus);
        return keyTechVM;
    }
}
