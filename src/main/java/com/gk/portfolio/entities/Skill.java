package com.gk.portfolio.entities;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Skill extends IdentifiedEntity<Long> {
    private String name;
    private BigDecimal experienceInYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getExperienceInYear() {
        return experienceInYear;
    }

    public void setExperienceInYear(BigDecimal experienceInYear) {
        this.experienceInYear = experienceInYear;
    }
}
