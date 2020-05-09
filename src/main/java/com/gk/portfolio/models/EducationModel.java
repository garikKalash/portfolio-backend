package com.gk.portfolio.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class EducationModel {
    @NotBlank
    public String name;
    @Min(1900)
    public Integer fromYear;
    @Min(1901)
    @Max(2100)
    public Integer toYear;
    public boolean present;
    @NotBlank
    public String level;
}
