package com.caecc.trlprj.repository;

import com.caecc.trlprj.domain.Note;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Note entity.
 */
@SuppressWarnings("unused")
public interface NoteRepository extends JpaRepository<Note,Long> {

}
