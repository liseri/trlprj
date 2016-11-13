package com.caecc.trlprj.domain;

import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.web.rest.vm.KeyTechVM;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Technology.
 */
@Entity
@Table(name = "keytech")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KeyTech extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    @ManyToOne
    @Column(name = "tech_id")
    private Long techId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "key_type")
    private KeyTechValueType keyValueType;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "from_user_fullname", length = 50, nullable = false)
    private String fromUserFullname;

    @Size(max = 50)
    @Column(name = "from_user_type", length = 50)
    private String fromUserType;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "to_user_fullname", length = 50, nullable = false)
    private String toUserFullname;

    @Size(max = 50)
    @Column(name = "to_user_type", length = 50)
    private String toUserType;

    @Size(max = 50)
    @Column(name = "value", length = 50)
    private String value;

    @Size(max = 3000)
    @Column(name = "value2", length = 3000, nullable = false)
    private String value2;

    @Size(max = 500)
    @Column(name = "note", length = 500)
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUserFullname() {
        return fromUserFullname;
    }

    public void setFromUserFullname(String fromUserFullname) {
        this.fromUserFullname = fromUserFullname;
    }

    public String getToUserFullname() {
        return toUserFullname;
    }

    public void setToUserFullname(String toUserFullname) {
        this.toUserFullname = toUserFullname;
    }

    public String getToUserType() {
        return toUserType;
    }

    public void setToUserType(String toUserType) {
        this.toUserType = toUserType;
    }

    public KeyTechValueType getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(KeyTechValueType keyValueType) {
        this.keyValueType = keyValueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(String fromUserType) {
        this.fromUserType = fromUserType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyTech technology = (KeyTech) o;
        if (technology.id == null || id == null) {
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
            ", tech='" + techId + "'" +
            ", keyValueType='" + keyValueType + "'" +
//            ", value='" + value + "'" +
//            ", value2='" + value2 + "'" +
            '}';
    }

    public static KeyTech fromKeyTechVM(Technology technology, KeyTechVM keyTechVM) {
        KeyTech newKeyTech = new KeyTech();
        newKeyTech.setId(keyTechVM.getId());
        newKeyTech.setTechId(technology.getId());
        newKeyTech.setKeyValueType(keyTechVM.getKeyValueType());
        newKeyTech.setValue(keyTechVM.getValue());
        newKeyTech.setValue2(keyTechVM.getValue2());
        newKeyTech.setNote(keyTechVM.getNote());
        return newKeyTech;
    }


    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }
}
