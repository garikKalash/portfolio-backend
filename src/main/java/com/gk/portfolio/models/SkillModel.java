package com.gk.portfolio.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SkillModel {
    @NotBlank
    public String name;

    @NotNull
    @DecimalMin("0.1")
    public BigDecimal experienceInYear;
}
