package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Branch;
import com.caecc.trlprj.domain.KeyTech;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
public interface KeyTechRepository extends JpaRepository<KeyTech, Long> {
    Page<KeyTech> findAllByTechIdAndKeyValueType(Long techId, KeyTechValueType keyTechValueType, Pageable pageable);
}
