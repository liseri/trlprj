package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.domain.enumeration.KeyTechValueType;
import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class KeyTechVM {
    private Long techId;
    private String userLogin;
    private KeyTechValueType keyValueType;
    private List<KeyTechValue> keyValues;
    private TRL trl;
    private TCL tcl;
    private String note;

    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public KeyTechValueType getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(KeyTechValueType keyValueType) {
        this.keyValueType = keyValueType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<KeyTechValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyTechValue> keyValues) {
        this.keyValues = keyValues;
    }

    public TRL getTrl() {
        return trl;
    }

    public void setTrl(TRL trl) {
        this.trl = trl;
    }

    public TCL getTcl() {
        return tcl;
    }

    public void setTcl(TCL tcl) {
        this.tcl = tcl;
    }


}
