package com.gk.portfolio.controllers;

import com.gk.portfolio.config.ModelMapperConfig;
import com.gk.portfolio.dto.EducationDTO;
import com.gk.portfolio.dto.ExperienceDTO;
import com.gk.portfolio.dto.LanguageDTO;
import com.gk.portfolio.dto.SkillDTO;
import com.gk.portfolio.entities.*;
import com.gk.portfolio.models.EducationModel;
import com.gk.portfolio.models.ExperienceModel;
import com.gk.portfolio.models.LanguageModel;
import com.gk.portfolio.models.SkillModel;
import com.gk.portfolio.services.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/gk/cv")
@Validated
public class CVController {
    @Autowired
    CVService cvService;
    @Autowired
    ModelMapperConfig modelMapper;


    @GetMapping
    public CV cv() {
        return cvService.getCv();
    }


    @Deprecated
    @PostMapping("/language")
    @PreAuthorize("hasRole('me')")
    public Language createLanguage(@Valid @RequestBody LanguageModel languageModel) {
        return cvService.saveLanguage(languageModel);
    }


    @PostMapping("/language/new")
    @PreAuthorize("hasRole('me')")
    public LanguageDTO createLanguageNew(@Valid @RequestBody LanguageModel languageModel) {
        return modelMapper.map(cvService.saveLanguage(languageModel), LanguageDTO.class);

    }


    @DeleteMapping("/language/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeLanguage(@PathVariable("id") Long id) {
        cvService.removeLanguage(id);
    }

    @PutMapping("/language/{id}")
    @PreAuthorize("hasRole('me')")
    public LanguageDTO updateLanguage(@PathVariable("id") Long id, @Valid @RequestBody LanguageModel languageModel) {
        return modelMapper.map(cvService.updateLanguage(id, languageModel), LanguageDTO.class);
    }

    @Deprecated
    @PostMapping("/education")
    @PreAuthorize("hasRole('me')")
    public Education createEducation(@Valid @RequestBody EducationModel educationModel) {
        return cvService.saveEducation(educationModel);
    }

    @PostMapping("/education/new")
    @PreAuthorize("hasRole('me')")
    public EducationDTO createEducationNew(@Valid @RequestBody EducationModel educationModel) {
        return modelMapper.map(cvService.saveEducation(educationModel), EducationDTO.class);
    }

    @DeleteMapping("/education/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeEducation(@PathVariable("id") Long id) {
        cvService.removeEducation(id);
    }

    @PutMapping("/education/{id}")
    @PreAuthorize("hasRole('me')")
    public EducationDTO updateEducation(@PathVariable("id") Long id, @Valid @RequestBody EducationModel educationModel) {
        return modelMapper.map(cvService.updateEducation(id, educationModel), EducationDTO.class);
    }

    @Deprecated
    @PostMapping("/skill")
    @PreAuthorize("hasRole('me')")
    public Skill createSkill(@Valid @RequestBody SkillModel skill) {
        return cvService.saveSkill(skill);
    }

    @PostMapping("/skill/new")
    @PreAuthorize("hasRole('me')")
    public SkillDTO createSkillNew(@Valid @RequestBody SkillModel skill) {
        return modelMapper.map(cvService.saveSkill(skill), SkillDTO.class);
    }

    @DeleteMapping("/skill/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeSkill(@PathVariable("id") Long id) {
        cvService.removeSkill(id);
    }


    @PutMapping("/skill/{id}")
    @PreAuthorize("hasRole('me')")
    public SkillDTO updateSkill(@PathVariable("id") Long id, @Valid @RequestBody SkillModel skillModel) {
        return modelMapper.map(cvService.updateSkill(id, skillModel), SkillDTO.class);
    }

    @Deprecated
    @PostMapping("/experience")
    @PreAuthorize("hasRole('me')")
    public Experience createExperience(@RequestBody ExperienceModel experienceModel) {
        return cvService.saveExperience(experienceModel);
    }

    @PostMapping("/experience/new")
    @PreAuthorize("hasRole('me')")
    public ExperienceDTO createExperienceNew(@RequestBody ExperienceModel experienceModel) {
        return modelMapper.map(cvService.saveExperience(experienceModel), ExperienceDTO.class);
    }

    @DeleteMapping("/experience/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeExperience(@PathVariable("id") Long id) {
        cvService.removeExperience(id);
    }

    @PutMapping("/experience/{id}")
    @PreAuthorize("hasRole('me')")
    public ExperienceDTO updateExperience(@PathVariable("id") Long id, @Valid @RequestBody ExperienceModel experienceModel) {
        return modelMapper.map(cvService.updateExperience(id, experienceModel), ExperienceDTO.class);
    }


}
