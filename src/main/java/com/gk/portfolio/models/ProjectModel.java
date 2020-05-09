package com.gk.portfolio.models;

import javax.validation.constraints.NotBlank;

public class ProjectModel {
    public Long id;
    @NotBlank
    public String name;
    @NotBlank
    public String description;
    @NotBlank
    public String techDescription;
    @NotBlank
    public String projectType;
}
