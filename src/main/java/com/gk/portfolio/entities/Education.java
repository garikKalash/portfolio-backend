package com.gk.portfolio.entities;

import javax.persistence.Entity;

@Entity
public class Education extends IdentifiedEntity<Long>{
    private String name;
    private Integer fromYear;
    private Integer toYear;
    private Boolean present;
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
