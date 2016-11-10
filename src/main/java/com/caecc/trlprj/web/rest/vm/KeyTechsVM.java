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
    private KeyTechValueType keyValueType;
    private List<KeyTechValue> keyValues;

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

    public List<KeyTechValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyTechValue> keyValues) {
        this.keyValues = keyValues;
    }
}
