package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.enumeration.PrjStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ProjectVM {

    private Long id;

    @Size(max = 50)
    private String name;

    private PrjStatus statu;

    @Size(max = 500)
    private String descript1;

    @Size(max = 500)
    private String descript2;

    @Size(max = 500)
    private String descript3;

    @Size(max = 500)
    private String descript4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrjStatus getStatu() {
        return statu;
    }

    public void setStatu(PrjStatus statu) {
        this.statu = statu;
    }

    public String getDescript1() {
        return descript1;
    }

    public void setDescript1(String descript1) {
        this.descript1 = descript1;
    }

    public String getDescript2() {
        return descript2;
    }

    public void setDescript2(String descript2) {
        this.descript2 = descript2;
    }

    public String getDescript3() {
        return descript3;
    }

    public void setDescript3(String descript3) {
        this.descript3 = descript3;
    }

    public String getDescript4() {
        return descript4;
    }

    public void setDescript4(String descript4) {
        this.descript4 = descript4;
    }

    public ProjectVM from(Project prj) {
        this.id = prj.getId();
        this.name = prj.getName();
        this.statu = prj.getStatu();
        this.descript1 = prj.getDescript1();
        this.descript2 = prj.getDescript2();
        this.descript3 = prj.getDescript3();
        this.descript4 = prj.getDescript4();
        return this;
    }


}
