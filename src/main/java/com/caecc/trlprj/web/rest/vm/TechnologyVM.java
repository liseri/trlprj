package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.domain.Project;
import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;

import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2016/10/10.
 */
public class TechnologyVM {

    private Long id;

    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String descript;

    private boolean isKey;

    private Long prjId;

    private Long parentTechId;

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

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    public Long getParentTechId() {
        return parentTechId;
    }

    public void setParentTechId(Long parentTechId) {
        this.parentTechId = parentTechId;
    }

    public Technology toTechnology(Technology parentTech, User creator){
        return new Technology()
            .name(name)
            .descript(descript)
            .isKey(false)
//            .prj(project)
            .parentTech(parentTech)
            .creator(creator);
    }

}
