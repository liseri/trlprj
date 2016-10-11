package com.caecc.trlprj.service;

import com.caecc.trlprj.domain.Branch;
import com.caecc.trlprj.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Branch.
 */
@Service
@Transactional
public class BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchService.class);

    @Inject
    private BranchRepository branchRepository;

    /**
     * Save a branch.
     *
     * @param branch the entity to save
     * @return the persisted entity
     */
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        Branch result = branchRepository.save(branch);
        return result;
    }

    /**
     *  Get all the branches.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Branch> findAll() {
        log.debug("Request to get all Branches");
        List<Branch> result = branchRepository.findAll();

        return result;
    }

    /**
     *  Get one branch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Branch findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        Branch branch = branchRepository.findOne(id);
        return branch;
    }

    /**
     *  Delete the  branch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.delete(id);
    }
}
