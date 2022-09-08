package com.gk.portfolio.models;

import org.hibernate.annotations.Check;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserModel {

    @NotBlank
    public String name;
    @NotBlank
    public String surname;
    @NotBlank
    public String password;
    @NotBlank
    @Check(constraints = "role in ('technical_guy', 'hr', 'me')")
    public String role;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    public String phone;
    @NotBlank
    public String address;
    @NotBlank
    public String skype;
}
