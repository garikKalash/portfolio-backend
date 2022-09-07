package com.gk.portfolio.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FeedbackModel {

    @NotBlank
    public String name;
    @NotBlank
    public String position;

    @NotBlank
    @Email
    public String email;

    @Min(1)
    public Integer duration;
    @NotBlank
    @Size(min = 5, max = 500)
    public String text;

}
