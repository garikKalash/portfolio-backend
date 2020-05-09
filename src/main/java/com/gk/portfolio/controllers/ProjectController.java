package com.gk.portfolio.controllers;

import com.gk.portfolio.entities.Project;
import com.gk.portfolio.models.ProjectModel;
import com.gk.portfolio.services.ProjectService;
import com.gk.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Validated
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping
    public List<Project> findAll() {
        String role = getRequesterRole();
        if (!role.equals("me")) {
            return projectService.getByType("office", role.equals("hr"));
        } else {
            return projectService.getAll();
        }
    }

    @GetMapping("/tech/{name}")
    @PreAuthorize("hasRole('me') || hasRole('technical_guy')")
    public String getTechDescription(@PathVariable("name") String name) {
        return projectService.getTechDescription(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('me')")
    public Project save(@Valid @RequestBody ProjectModel projectModel) {
        return projectService.save(projectModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public void deleteProject(@PathVariable("id") Long id) {
        projectService.delete(id);
    }

    private String getRequesterRole() {
        UserService.SecuredUser securedUser = ((UserService.SecuredUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return securedUser.getUser().getRole();
    }
}
