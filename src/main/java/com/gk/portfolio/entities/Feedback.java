package com.gk.portfolio.entities;

import javax.persistence.Entity;


@Entity
public class Feedback extends IdentifiedEntity<Long>{
    private String name;
    private String position;
    private Integer durationInMans;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDurationInMans() {
        return durationInMans;
    }

    public void setDurationInMans(Integer durationInMans) {
        this.durationInMans = durationInMans;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
