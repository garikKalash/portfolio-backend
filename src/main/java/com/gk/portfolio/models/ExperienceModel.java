package com.gk.portfolio.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ExperienceModel {

    @Min(1900)
    public Integer fromYear;

    @Min(1901)
    @Max(2100)
    public Integer toYear;

    @NotBlank
    public String company;
    @NotBlank
    public String workRole;
    @NotBlank
    public String description;

    public boolean present;
    @NotBlank
    public String avatarLink;
}
