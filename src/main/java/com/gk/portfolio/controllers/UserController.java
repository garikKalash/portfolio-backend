package com.gk.portfolio.controllers;

import com.gk.portfolio.entities.User;
import com.gk.portfolio.models.UserModel;
import com.gk.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/profile")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public User updateProfile(@PathVariable("id") Long id, @Valid @RequestBody UserModel userModel) {
        return userService.update(id, userModel);
    }


}
