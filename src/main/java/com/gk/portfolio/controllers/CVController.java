package com.gk.portfolio.controllers;

import com.gk.portfolio.entities.*;
import com.gk.portfolio.models.EducationModel;
import com.gk.portfolio.models.LanguageModel;
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
    @Autowired CVService cvService;

    @GetMapping
    public CV cv(){
        return cvService.getCv();
    }

    @PostMapping("/language")
    @PreAuthorize("hasRole('me')")
    public Language createLanguage(@Valid @RequestBody LanguageModel languageModel) {
        Language language = new Language();
        language.setLevel(languageModel.level);
        language.setName(languageModel.name);
        return cvService.saveLanguage(language);
    }

    @DeleteMapping("/language/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeLanguage(@PathVariable("id") Long id) {
        cvService.removeLanguage(id);
    }

    @PostMapping("/education")
    @PreAuthorize("hasRole('me')")
    public Education createEducation(@Valid @RequestBody EducationModel educationModel) {
        return cvService.saveEducation(educationModel);
    }

    @DeleteMapping("/education/{id}")
    @PreAuthorize("hasRole('me')")
    public void updateEducation(@PathVariable("id") Long id) {
        cvService.removeEducation(id);
    }

    @PostMapping("/skill")
    @PreAuthorize("hasRole('me')")
    public Skill createSkill(@Valid @RequestBody Skill skill) {
        return cvService.saveSkill(skill);
    }

    @PostMapping("/experience")
    @PreAuthorize("hasRole('me')")
    public Experience createExperience(@RequestBody Experience experience) {
        return cvService.saveExperience(experience);
    }

    @DeleteMapping("/experience/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeExperience(@PathVariable("id") Long id) {
        cvService.removeExperience(id);
    }


    @DeleteMapping("/skill/{id}")
    @PreAuthorize("hasRole('me')")
    public void removeSkill(@PathVariable("id") Long id) {
        cvService.removeSkill(id);
    }
}
