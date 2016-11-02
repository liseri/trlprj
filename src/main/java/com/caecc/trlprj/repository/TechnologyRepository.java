package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Technology;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Technology entity.
 */
@SuppressWarnings("unused")
public interface TechnologyRepository extends JpaRepository<Technology,Long> {

    @Query("select technology from Technology technology where technology.prj.id =:prjId")
    List<Technology> findByProjectId(@Param("prjId") Long prjId);

    @Query("select technology from Technology technology where technology.creator.login = ?#{principal.username}")
    List<Technology> findByCreatorIsCurrentUser();

    @Query("select distinct technology from Technology technology left join fetch technology.subCreators")
    List<Technology> findAllWithEagerRelationships();

    @Query("select technology from Technology technology left join fetch technology.subCreators where technology.id =:id")
    Technology findOneWithEagerRelationships(@Param("id") Long id);

}
