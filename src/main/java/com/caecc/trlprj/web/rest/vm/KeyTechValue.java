package com.caecc.trlprj.web.rest.vm;

/**
 * Created by Administrator on 2016/11/7.
 */
public class KeyTechValue {
    private String fromUserFullName;
    private String fromUserType;
    private String toUserFullName;
    private String toUserType;
    private String value;
    private String value2;
    private String note;

    public String getFromUserFullName() {
        return fromUserFullName;
    }

    public void setFromUserFullName(String fromUserFullName) {
        this.fromUserFullName = fromUserFullName;
    }

    public String getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(String fromUserType) {
        this.fromUserType = fromUserType;
    }

    public String getToUserFullName() {
        return toUserFullName;
    }

    public void setToUserFullName(String toUserFullName) {
        this.toUserFullName = toUserFullName;
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

    public String getToUserType() {
        return toUserType;
    }

    public void setToUserType(String toUserType) {
        this.toUserType = toUserType;
    }
}
