package com.gk.portfolio.controllers;

import com.gk.portfolio.config.ModelMapperConfig;
import com.gk.portfolio.dto.ProjectDto;
import com.gk.portfolio.dto.ProjectNoTechDTO;
import com.gk.portfolio.dto.ProjectTechDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/project")
@Validated
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    ModelMapperConfig modelMapper;

    @Deprecated
    @GetMapping()
    public List<Project> findAll() {
        String role = getRequesterRole();
        if (!role.equals("me")) {
            return projectService.getByType("office", role.equals("hr"));
        } else {
            return projectService.getAll();
        }
    }
    @GetMapping("/new")
    public List<ProjectDto> findAllNew() {
        String role = getRequesterRole();
        if (!role.equals("me")) {
            return projectService.getByType("office", role.equals("hr")).stream()
                    .map(project -> modelMapper.map(project, ProjectNoTechDTO.class))
                    .collect(Collectors.toList());
        } else {
            return projectService.getAll().stream()
                    .map(project -> modelMapper.map(project, ProjectTechDTO.class))
                    .collect(Collectors.toList());
        }
    }



    @GetMapping("/tech/{name}")
    @PreAuthorize("hasRole('me') || hasRole('technical_guy')")
    public String getTechDescription(@PathVariable("name") String name) {
        return projectService.getTechDescription(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('me')")
    public ProjectTechDTO save(@Valid @RequestBody ProjectModel projectModel) {
        Project project = projectService.save(projectModel);
        return modelMapper.map(project, ProjectTechDTO.class);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public void delete(@PathVariable("id") Long id) {
        projectService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public ProjectTechDTO update(@PathVariable("id") Long id, @Valid @RequestBody ProjectModel projectModel) {
       Project project = projectService.update(id, projectModel);
        return modelMapper.map(project, ProjectTechDTO.class);
    }

    private String getRequesterRole() {
        UserService.SecuredUser securedUser = ((UserService.SecuredUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return securedUser.getUser().getRole();
    }
}
