package com.caecc.trlprj.web.rest.vm;

import com.caecc.trlprj.domain.Technology;
import com.caecc.trlprj.domain.User;
import com.caecc.trlprj.domain.enumeration.TCL;
import com.caecc.trlprj.domain.enumeration.TRL;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/10/10.
 */
public class Technology2VM extends TechnologyVM {
    private boolean asCreator;
    private boolean asParentCreator;
    private boolean asSubCreator;
    private String creator;
    private String[] subCreator;
    @Enumerated(EnumType.STRING)
    private TCL tcl;
    @Enumerated(EnumType.STRING)
    private TRL trl;
    private List<Technology2VM> subTechs;

    public TCL getTcl() {
        return tcl;
    }

    public void setTcl(TCL tcl) {
        this.tcl = tcl;
    }

    public TRL getTrl() {
        return trl;
    }

    public void setTrl(TRL trl) {
        this.trl = trl;
    }

    public String[] getSubCreator() {
        return subCreator;
    }

    public void setSubCreator(String[] subCreator) {
        this.subCreator = subCreator;
    }

    public boolean isAsCreator() {
        return asCreator;
    }

    public void setAsCreator(boolean asCreator) {
        this.asCreator = asCreator;
    }

    public boolean isAsParentCreator() {
        return asParentCreator;
    }

    public void setAsParentCreator(boolean asParentCreator) {
        this.asParentCreator = asParentCreator;
    }

    public boolean isAsSubCreator() {
        return asSubCreator;
    }

    public void setAsSubCreator(boolean asSubCreator) {
        this.asSubCreator = asSubCreator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Technology2VM> getSubTechs() {
        return subTechs;
    }

    public void setSubTechs(List<Technology2VM> subTechs) {
        this.subTechs = subTechs;
    }
    /**
     * 非结点创建者和拥有者的研发人员
     * @param tech
     * @return
     */
    public static Technology2VM fromTechForGeneralViewer(Technology tech, boolean asSubCreator) {
        Technology2VM technology2VM = new Technology2VM();
        technology2VM.setId(tech.getId());
        technology2VM.setName(tech.getName());
//        technology2VM.setDescript(tech.getDescript());
        technology2VM.setKey(tech.isIsKey());
        technology2VM.setPrjId(tech.getPrj().getId());
        technology2VM.setParentTechId(tech.getParentTech()==null?null:tech.getParentTech().getId());
//        technology2VM.setTcl(tech.getTcl());
//        technology2VM.setTrl(tech.getTrl());
        technology2VM.setAsCreator(false);
        technology2VM.setAsParentCreator(false);
        technology2VM.setAsSubCreator(asSubCreator);
        return technology2VM;
    }

    /**
     * TRL专业人员和评审员
     * @param tech
     * @return
     */
    public static Technology2VM fromTechForBigViewer(Technology tech) {
        Technology2VM technology2VM = new Technology2VM();
        technology2VM.setId(tech.getId());
        technology2VM.setName(tech.getName());
        technology2VM.setDescript(tech.getDescript());
        technology2VM.setKey(tech.isIsKey());
        technology2VM.setPrjId(tech.getPrj().getId());
        technology2VM.setParentTechId(tech.getParentTech()==null?null:tech.getParentTech().getId());
        technology2VM.setTcl(tech.getTcl());
        technology2VM.setTrl(tech.getTrl());
        technology2VM.setAsCreator(false);
        technology2VM.setAsParentCreator(false);
        technology2VM.setAsSubCreator(false);
        return technology2VM;
    }

    /**
     * 节点创建者或节点拥有者(父节点创建者)
     * 创建者同时也是子结点创建人
     * @param tech
     * @return
     */
    public static Technology2VM fromTechForCreatorOrParentCreator(Technology tech, boolean asCreator, boolean asParentCreator) {
        Technology2VM technology2VM = Technology2VM.fromTechForBigViewer(tech);
        technology2VM.setId(tech.getId());
        technology2VM.setName(tech.getName());
        technology2VM.setDescript(tech.getDescript());
        technology2VM.setKey(tech.isIsKey());
        technology2VM.setPrjId(tech.getPrj().getId());
        technology2VM.setParentTechId(tech.getParentTech()==null?null:tech.getParentTech().getId());
        technology2VM.setTcl(tech.getTcl());
        technology2VM.setTrl(tech.getTrl());
        technology2VM.setCreator(tech.getCreator().getFullName());
        technology2VM.setSubCreator(tech.getSubCreators().stream()
            .map(user->user.getFullName())
            .collect(Collectors.toList())
            .toArray(new String[0]));
        technology2VM.setAsCreator(asCreator);
        technology2VM.setAsParentCreator(asParentCreator);
        technology2VM.setAsSubCreator(asCreator);

        return technology2VM;
    }
}
