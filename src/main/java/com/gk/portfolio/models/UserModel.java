package com.gk.portfolio.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserModel {

    @NotBlank public String telephone;

    @NotBlank @Email public String email;

    @NotBlank public String skype;

    @NotBlank public String address;
}
