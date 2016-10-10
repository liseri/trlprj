package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.creator.login = ?#{principal.username}")
    List<Project> findByCreatorIsCurrentUser();

    @Query("select distinct project from Project project left join fetch project.trlers left join fetch project.evlers")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.trlers left join fetch project.evlers where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

}
