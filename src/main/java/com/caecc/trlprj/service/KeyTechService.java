package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.*;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.domain.enumeration.PrjStatus;
import com.caecc.trlprj.repository.KeyNoteRepository;
import com.caecc.trlprj.repository.ProjectRepository;
import com.caecc.trlprj.repository.TechnologyRepository;
import com.caecc.trlprj.repository.UserRepository;
import com.caecc.trlprj.security.AuthoritiesConstants;
import com.caecc.trlprj.web.rest.vm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private KeyNoteRepository keyNoteRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;


    public KeyTechVM saveKeyTechOfCreator(Technology technology, String user, KeyTechVM keyTechVM) {
        log.debug("Request to saveKeyTechOfCreator : {}", keyTechVM);
        return null;
    }

    /**
     * TRL专业人员请求数据
     * @param technology
     * @param keyValueType
     * @return
     */
    public KeyTechVM getKeyTech(Technology technology, String user, KeyTechValueType keyValueType) {
        List<KeyTechValue> keyValus = technology.getKeyValues()
            .stream()
            .filter(keyTech -> keyTech.getKeyValueType().equals(keyValueType) &&
                keyTech.getUserLogin().equalsIgnoreCase(user))
            .map(keyTech -> new KeyTechValue(keyTech.getValue1(), keyTech.getValue2(), keyTech.getValue3()))
            .collect(Collectors.toList());
        KeyNote keyNote = keyNoteRepository.findOneByTechIdAndUserLoginAndKeyValueType(technology.getId(), user, keyValueType).orElse(null);
        KeyTechVM keyTechVM = new KeyTechVM();
        keyTechVM.setKeyValueType(keyValueType);
        keyTechVM.setUserLogin(user);
        keyTechVM.setTcl(technology.getTcl());
        keyTechVM.setTrl(technology.getTrl());
        keyTechVM.setKeyValues(keyValus);
        keyTechVM.setNote(keyNote==null?"":keyNote.getNote());
        return keyTechVM;
    }
}
