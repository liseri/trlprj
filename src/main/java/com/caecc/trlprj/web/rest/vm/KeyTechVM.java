package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.config.Constants;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.enumeration.KeyTechValueType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class KeyTechVM {

    private Long id;

    private Long techId;

    private KeyTechValueType keyValueType;

    private Long toId;

    @Size(max = 50)
    private String value;

    @Size(max = 3000)
    private String value2;

    @Size(max = 500)
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }
}
