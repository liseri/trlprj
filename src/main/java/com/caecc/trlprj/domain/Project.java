package com.caecc.trlprj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
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

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "complete_time")
    private ZonedDateTime completeTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Technology rootTech;

    @ManyToOne
    private User creator;

    @ManyToOne
    private Technology tech;

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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Project startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getCompleteTime() {
        return completeTime;
    }

    public Project completeTime(ZonedDateTime completeTime) {
        this.completeTime = completeTime;
        return this;
    }

    public void setCompleteTime(ZonedDateTime completeTime) {
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

    public Technology getTech() {
        return tech;
    }

    public Project tech(Technology technology) {
        this.tech = technology;
        return this;
    }

    public void setTech(Technology technology) {
        this.tech = technology;
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
