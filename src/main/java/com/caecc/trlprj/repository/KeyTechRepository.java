package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Branch;
import com.caecc.trlprj.domain.KeyTech;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
public interface KeyTechRepository extends JpaRepository<KeyTech, Long> {

}
