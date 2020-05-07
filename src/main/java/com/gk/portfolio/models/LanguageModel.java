package com.gk.portfolio.models;

import javax.validation.constraints.NotBlank;

public class LanguageModel {
    @NotBlank public String name;
    @NotBlank public String level;
}
