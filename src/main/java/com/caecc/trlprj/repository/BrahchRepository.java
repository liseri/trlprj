package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Brahch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Brahch entity.
 */
@SuppressWarnings("unused")
public interface BrahchRepository extends JpaRepository<Brahch,Long> {

}
