package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Authority;
import com.caecc.trlprj.domain.KeyTech;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;
import com.caecc.trlprj.repository.KeyTechRepository;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.repository.UserRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.web.rest.vm.KeyTechVM;
import com.caecc.trlprj.web.rest.vm.KeyTechValue;
import com.caecc.trlprj.web.rest.vm.KeyTechsVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
        if (technology.getCreator().getLogin().equals(user.getLogin())) {
            keyTech.setFromUserType(AuthoritiesConstants.USER);
            //如果是创建人操作，则保存到Technology中
            if (!StringUtils.isEmpty(keyTechVM.getValue())) {
                //关键程度
                if (keyTechVM.getKeyValueType() == KeyTechValueType.KEY) {
                    if (keyTechVM.getValue().equalsIgnoreCase("1")) {
                        technology.setIsKey(true);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("2")) {
                        technology.setIsKey(false);
                        technologyRepository.save(technology);
                    }
                }
                //TCL
                else if (keyTechVM.getKeyValueType() == KeyTechValueType.TCL) {
                    if (keyTechVM.getValue().equalsIgnoreCase("TCLA")) {
                        technology.setTcl(TCL.TCLA);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TCLB")) {
                        technology.setTcl(TCL.TCLB);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TCLC")) {
                        technology.setTcl(TCL.TCLC);
                        technologyRepository.save(technology);
                    }
                }
                //TRL
                else if (keyTechVM.getKeyValueType() == KeyTechValueType.TRL) {
                    if (keyTechVM.getValue().equalsIgnoreCase("TRL1")) {
                        technology.setTrl(TRL.TRL1);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL2")) {
                        technology.setTrl(TRL.TRL2);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL3")) {
                        technology.setTrl(TRL.TRL3);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL4")) {
                        technology.setTrl(TRL.TRL4);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL5")) {
                        technology.setTrl(TRL.TRL5);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL6")) {
                        technology.setTrl(TRL.TRL6);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL7")) {
                        technology.setTrl(TRL.TRL7);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL8")) {
                        technology.setTrl(TRL.TRL8);
                        technologyRepository.save(technology);
                    }
                    else if (keyTechVM.getValue().equalsIgnoreCase("TRL9")) {
                        technology.setTrl(TRL.TRL9);
                        technologyRepository.save(technology);
                    }
                }
            }
        } else if (technology.getPrj().getTrlers().contains(user))
            keyTech.setFromUserType(AuthoritiesConstants.TRL);
        else if (technology.getPrj().getEvlers().contains(user))
            keyTech.setFromUserType(AuthoritiesConstants.EVL);
        else if (user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN)))
            keyTech.setFromUserType(AuthoritiesConstants.ADMIN);
        else return null;
        //ToUser  如果ToId为空，则默认认为回复给创建人
        if (keyTechVM.getToId() == null || keyTechVM.getToId() < 0) {
            keyTech.setToUserFullname(technology.getCreator().getFullName());
            keyTech.setToUserType(AuthoritiesConstants.USER);
        } else {
            KeyTech toKeyTech = keyTechRepository.findOne(keyTechVM.getToId());
            keyTech.setToUserFullname(toKeyTech.getFromUserFullname());
            keyTech.setToUserType(toKeyTech.getFromUserType());
        }
        return keyTechRepository.save(keyTech);
    }

    private User getUserFromFullName(String userFullName) {
        String[] userInfos = userFullName.split(User.FULL_NAME_SPLITER);
        String userName = userInfos.length > 0 ? userInfos[0] : "";
        String branch = userInfos.length > 1 ? userInfos[1] : "";
        return userService.getUserWithAuthoritiesByNameAndBranch(userName, branch).get();
    }

    public KeyTechsVM getKeyTech(Technology technology, KeyTechValueType keyValueType, Pageable pageable) {
        User user = userService.getUserWithAuthorities();
        boolean isAdmin = user.getAuthorities().contains(new Authority().name(AuthoritiesConstants.ADMIN));
        String userAuthor = "";
        if (isAdmin)
            userAuthor = AuthoritiesConstants.ADMIN;
        else if (technology.getPrj().getTrlers().contains(user))
            userAuthor = AuthoritiesConstants.TRL;
        else if (technology.getPrj().getEvlers().contains(user))
            userAuthor = AuthoritiesConstants.EVL;
        else if (technology.getCreator().getLogin().equalsIgnoreCase(user.getLogin()))
            userAuthor = AuthoritiesConstants.USER;
        else return null;

        List<KeyTechValue> keyValus = keyTechRepository.findAllByTechIdAndKeyValueType(technology.getId(), keyValueType, pageable).getContent()
            .stream()
//            .filter(keyTech -> keyTech.getKeyValueType().equals(keyValueType))
            .map(keyTech -> {
                KeyTechValue keyTechValue = new KeyTechValue();
                keyTechValue.setId(keyTech.getId());
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
        KeyTechValue creatorValue = keyValus
            .stream()
            .filter(keyValue -> keyValue.getFromUserType().equalsIgnoreCase(AuthoritiesConstants.USER) &&
                keyValue.getToUserType().equalsIgnoreCase(AuthoritiesConstants.USER))
            .findFirst()
            .orElse(new KeyTechValue());
        KeyTechsVM keyTechVM = new KeyTechsVM();
        keyTechVM.setTechId(technology.getId());
        keyTechVM.setType(keyValueType);
        keyTechVM.setValues(keyValus);
        keyTechVM.setMyUserFullName(user.getFullName());
        keyTechVM.setMyUserType(userAuthor);
        keyTechVM.setCreatorValue(creatorValue);
        return keyTechVM;
    }
}
