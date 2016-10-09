package com.caecc.trlprj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.caecc.trlprj.domain.enumeration.NoteType;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "tech_id", nullable = false)
    private Long techId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "note_type", nullable = false)
    private NoteType noteType;

    @Column(name = "bool_value")
    private Boolean boolValue;

    @Column(name = "number_value")
    private Integer numberValue;

    @Size(max = 500)
    @Column(name = "note_text", length = 500)
    private String noteText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechId() {
        return techId;
    }

    public Note techId(Long techId) {
        this.techId = techId;
        return this;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public Note noteType(NoteType noteType) {
        this.noteType = noteType;
        return this;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public Boolean isBoolValue() {
        return boolValue;
    }

    public Note boolValue(Boolean boolValue) {
        this.boolValue = boolValue;
        return this;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public Note numberValue(Integer numberValue) {
        this.numberValue = numberValue;
        return this;
    }

    public void setNumberValue(Integer numberValue) {
        this.numberValue = numberValue;
    }

    public String getNoteText() {
        return noteText;
    }

    public Note noteText(String noteText) {
        this.noteText = noteText;
        return this;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Note note = (Note) o;
        if(note.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + id +
            ", techId='" + techId + "'" +
            ", noteType='" + noteType + "'" +
            ", boolValue='" + boolValue + "'" +
            ", numberValue='" + numberValue + "'" +
            ", noteText='" + noteText + "'" +
            '}';
    }
}
