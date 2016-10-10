package com.caecc.trlprj.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.caecc.trlprj.domain.enumeration.PrjStatus;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statu", nullable = false)
    private PrjStatus statu;

    @NotNull
    @Size(max = 500)
    @Column(name = "descript_1", length = 500, nullable = false)
    private String descript1;

    @NotNull
    @Size(max = 500)
    @Column(name = "descript_2", length = 500, nullable = false)
    private String descript2;

    @NotNull
    @Size(max = 500)
    @Column(name = "descript_3", length = 500, nullable = false)
    private String descript3;

    @NotNull
    @Size(max = 500)
    @Column(name = "descript_4", length = 500, nullable = false)
    private String descript4;

    @Size(max = 30)
    @Column(name = "start_time", length = 30)
    private String startTime;

    @Size(max = 30)
    @Column(name = "complete_time", length = 30)
    private String completeTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Technology rootTech;

    @OneToMany(mappedBy = "prj")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Technology> teches = new HashSet<>();

    @ManyToOne
    private User creator;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_trlers",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="trlers_id", referencedColumnName="ID"))
    private Set<User> trlers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_evlers",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="evlers_id", referencedColumnName="ID"))
    private Set<User> evlers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrjStatus getStatu() {
        return statu;
    }

    public Project statu(PrjStatus statu) {
        this.statu = statu;
        return this;
    }

    public void setStatu(PrjStatus statu) {
        this.statu = statu;
    }

    public String getDescript1() {
        return descript1;
    }

    public Project descript1(String descript1) {
        this.descript1 = descript1;
        return this;
    }

    public void setDescript1(String descript1) {
        this.descript1 = descript1;
    }

    public String getDescript2() {
        return descript2;
    }

    public Project descript2(String descript2) {
        this.descript2 = descript2;
        return this;
    }

    public void setDescript2(String descript2) {
        this.descript2 = descript2;
    }

    public String getDescript3() {
        return descript3;
    }

    public Project descript3(String descript3) {
        this.descript3 = descript3;
        return this;
    }

    public void setDescript3(String descript3) {
        this.descript3 = descript3;
    }

    public String getDescript4() {
        return descript4;
    }

    public Project descript4(String descript4) {
        this.descript4 = descript4;
        return this;
    }

    public void setDescript4(String descript4) {
        this.descript4 = descript4;
    }

    public String getStartTime() {
        return startTime;
    }

    public Project startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public Project completeTime(String completeTime) {
        this.completeTime = completeTime;
        return this;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Technology getRootTech() {
        return rootTech;
    }

    public Project rootTech(Technology technology) {
        this.rootTech = technology;
        return this;
    }

    public void setRootTech(Technology technology) {
        this.rootTech = technology;
    }

    public Set<Technology> getTeches() {
        return teches;
    }

    public Project teches(Set<Technology> technologies) {
        this.teches = technologies;
        return this;
    }

    public Project addTech(Technology technology) {
        teches.add(technology);
        technology.setPrj(this);
        return this;
    }

    public Project removeTech(Technology technology) {
        teches.remove(technology);
        technology.setPrj(null);
        return this;
    }

    public void setTeches(Set<Technology> technologies) {
        this.teches = technologies;
    }

    public User getCreator() {
        return creator;
    }

    public Project creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Set<User> getTrlers() {
        return trlers;
    }

    public Project trlers(Set<User> users) {
        this.trlers = users;
        return this;
    }

    public Project addTrlers(User user) {
        trlers.add(user);
        return this;
    }

    public Project removeTrlers(User user) {
        trlers.remove(user);
        return this;
    }

    public void setTrlers(Set<User> users) {
        this.trlers = users;
    }

    public Set<User> getEvlers() {
        return evlers;
    }

    public Project evlers(Set<User> users) {
        this.evlers = users;
        return this;
    }

    public Project addEvlers(User user) {
        evlers.add(user);
        return this;
    }

    public Project removeEvlers(User user) {
        evlers.remove(user);
        return this;
    }

    public void setEvlers(Set<User> users) {
        this.evlers = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", statu='" + statu + "'" +
            ", descript1='" + descript1 + "'" +
            ", descript2='" + descript2 + "'" +
            ", descript3='" + descript3 + "'" +
            ", descript4='" + descript4 + "'" +
            ", startTime='" + startTime + "'" +
            ", completeTime='" + completeTime + "'" +
            '}';
    }
}
