package com.caecc.trlprj.domain;

import com.caecc.trlprj.config.Constants;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;
import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

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

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false)
    private String userLogin;

    @ManyToOne
    private Technology tech;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "key_type")
    private KeyTechValueType keyValueType;

    private int value1;

    private int value2;

    private int value3;

    public Technology getTech() {
        return tech;
    }

    public void setTech(Technology tech) {
        this.tech = tech;
    }

    public KeyTechValueType getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(KeyTechValueType keyValueType) {
        this.keyValueType = keyValueType;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        KeyTech technology = (KeyTech) o;
//        if(technology.id == null || id == null) {
//            return false;
//        }
//        return Objects.equals(id, technology.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(id);
//    }
//
//    @Override
//    public String toString() {
//        return "Technology{" +
//            "id=" + id +
//            ", tech='" + tech.getId() + "'" +
//            ", keyValueType='" + keyValueType + "'" +
//            ", value1='" + value1 + "'" +
//            ", value2='" + value2 + "'" +
//            '}';
//    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
