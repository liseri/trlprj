package com.caecc.trlprj.domain;

import com.caecc.trlprj.config.Constants;
import com.caecc.trlprj.domain.enumeration.KeyValueType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Technology.
 */
@Entity
@Table(name = "keynote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KeyNote extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "tech_id")
    private Long techId;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false)
    private String userLogin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "key_type")
    private KeyValueType keyValueType;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "note", length = 500, nullable = false)
    private String note;

    public KeyValueType getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(KeyValueType keyValueType) {
        this.keyValueType = keyValueType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyNote technology = (KeyNote) o;
        if(technology.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, technology.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Technology{" +
            "id=" + id +
            ", techId='" + techId + "'" +
            ", keyValueType='" + keyValueType + "'" +
            '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }
}
