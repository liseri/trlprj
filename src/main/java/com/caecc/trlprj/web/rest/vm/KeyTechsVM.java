package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class KeyTechsVM {
    private Long techId;
    private KeyTechValueType type;
    private String myUserFullName;
    private String myUserType;
    private KeyTechValue creatorValue;
    private List<KeyTechValue> values;

    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public KeyTechValueType getType() {
        return type;
    }

    public void setType(KeyTechValueType type) {
        this.type = type;
    }

    public List<KeyTechValue> getValues() {
        return values;
    }

    public void setValues(List<KeyTechValue> values) {
        this.values = values;
    }

    public String getMyUserFullName() {
        return myUserFullName;
    }

    public void setMyUserFullName(String myUserFullName) {
        this.myUserFullName = myUserFullName;
    }

    public String getMyUserType() {
        return myUserType;
    }

    public void setMyUserType(String myUserType) {
        this.myUserType = myUserType;
    }

    public KeyTechValue getCreatorValue() {
        return creatorValue;
    }

    public void setCreatorValue(KeyTechValue creatorValue) {
        this.creatorValue = creatorValue;
    }
}
