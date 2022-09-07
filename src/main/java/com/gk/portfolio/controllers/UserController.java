package com.gk.portfolio.controllers;

import com.gk.portfolio.config.ModelMapperConfig;
import com.gk.portfolio.dto.UserDTO;
import com.gk.portfolio.entities.User;
import com.gk.portfolio.models.UserModel;
import com.gk.portfolio.services.UserService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapperConfig modelMapper;

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable("id") Long id) {
       User user = userService.getById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public UserDTO updateProfile(@PathVariable("id") Long id, @Valid @RequestBody UserModel userModel) {
        User user = userService.update(id, userModel);
        return modelMapper.map(user, UserDTO.class);
    }


}
