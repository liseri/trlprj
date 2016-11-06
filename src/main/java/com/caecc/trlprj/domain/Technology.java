package com.caecc.trlprj.domain;

import com.caecc.trlprj.web.rest.vm.TechnologyVM;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

import com.caecc.trlprj.domain.enumeration.TCL;

import com.caecc.trlprj.domain.enumeration.TRL;

/**
 * A Technology.
 */
@Entity
@Table(name = "technology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Technology extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "order_id", length = 50, nullable = false)
    private String orderId;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "descript", length = 500)
    private String descript;

    @NotNull
    @Column(name = "is_key", nullable = false)
    private Boolean isKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "tcl")
    private TCL tcl;

    @Enumerated(EnumType.STRING)
    @Column(name = "trl")
    private TRL trl;

    @OneToMany(mappedBy = "parentTech", fetch = FetchType.EAGER)
    @OrderBy(value = "orderId asc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Technology> subTeches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy(value = "branch asc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "technology_sub_creator",
               joinColumns = @JoinColumn(name="technologies_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="sub_creators_id", referencedColumnName="ID"))
    private Set<User> subCreators = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Project prj;

    @ManyToOne
    private Technology parentTech;

    @OneToMany(mappedBy = "tech")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KeyTech> keyValues = new HashSet<>();

    public void updateFrom(TechnologyVM technologyVM) {
        this.name(technologyVM.getName())
            .descript(technologyVM.getDescript());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOrderId() {
        return orderId;
    }

    public Technology orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public Technology name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public Technology descript(String descript) {
        this.descript = descript;
        return this;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Boolean isIsKey() {
        return isKey;
    }

    public Technology isKey(Boolean isKey) {
        this.isKey = isKey;
        return this;
    }

    public void setIsKey(Boolean isKey) {
        this.isKey = isKey;
    }

    public TCL getTcl() {
        return tcl;
    }

    public Technology tcl(TCL tcl) {
        this.tcl = tcl;
        return this;
    }

    public void setTcl(TCL tcl) {
        this.tcl = tcl;
    }

    public TRL getTrl() {
        return trl;
    }

    public Technology trl(TRL trl) {
        this.trl = trl;
        return this;
    }

    public void setTrl(TRL trl) {
        this.trl = trl;
    }

    public List<Technology> getSubTeches() {
        return subTeches;
    }

    public Technology subTeches(List<Technology> technologies) {
        this.subTeches = technologies;
        return this;
    }

    public Technology addSubTech(Technology technology) {
        subTeches.add(technology);
        technology.setParentTech(this);
        return this;
    }

    public Technology removeSubTech(Technology technology) {
        subTeches.remove(technology);
        technology.setParentTech(null);
        return this;
    }

    public void setSubTeches(List<Technology> technologies) {
        this.subTeches = technologies;
    }

    public Set<KeyTech> getKeyValues() {
        return keyValues;
    }
    public Technology addKeyTech(KeyTech keyTech) {
        getKeyValues().add(keyTech);
        return this;
    }
    public Technology removeKeyTech(KeyTech keyTech) {
        getKeyValues().remove(keyTech);
        return this;
    }
    public void setKeyValues(Set<KeyTech> keyValues) {
        this.keyValues = keyValues;
    }

    public User getCreator() {
        return creator;
    }

    public Technology creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Set<User> getSubCreators() {
        return subCreators;
    }

    public Technology subCreators(Set<User> users) {
        this.subCreators = users;
        return this;
    }

    public Technology addSubCreator(User user) {
        subCreators.add(user);
        return this;
    }

    public Technology removeSubCreator(User user) {
        subCreators.remove(user);
        return this;
    }

    public void setSubCreators(Set<User> users) {
        this.subCreators = users;
    }

    public Project getPrj() {
        return prj;
    }

    public Technology prj(Project project) {
        this.prj = project;
        return this;
    }

    public void setPrj(Project project) {
        this.prj = project;
    }

    public Technology getParentTech() {
        return parentTech;
    }

    public Technology parentTech(Technology technology) {
        this.parentTech = technology;
        return this;
    }

    public void setParentTech(Technology technology) {
        this.parentTech = technology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Technology technology = (Technology) o;
        if(technology.id == null || id == null) {
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
            ", name='" + name + "'" +
            ", descript='" + descript + "'" +
            ", isKey='" + isKey + "'" +
            ", tcl='" + tcl + "'" +
            ", trl='" + trl + "'" +
            '}';
    }


}
