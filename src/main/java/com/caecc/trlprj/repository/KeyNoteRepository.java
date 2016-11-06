package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.KeyNote;
import com.caecc.trlprj.domain.KeyTech;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
public interface KeyNoteRepository extends JpaRepository<KeyNote, Long> {
    Optional<KeyNote> findOneByTechIdAndUserLoginAndKeyValueType(Long techId, String userLogin, KeyTechValueType keyValueType);
}
