package com.gk.portfolio.services;

import com.gk.portfolio.entities.*;
import com.gk.portfolio.models.EducationModel;
import com.gk.portfolio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class CVService {
    @Autowired EducationRepository educationRepository;
    @Autowired LanguageRepository languageRepository;
    @Autowired UserRepository userRepository;
    @Autowired SkillRepository skillRepository;
    @Autowired ExperienceRepository experienceRepository;

    public CV getCv() {
        CV cv = new CV();
        User me = userRepository.findByRole("me").get();
        cv.address = me.getAddress();
        cv.email = me.getEmail();
        cv.name = me.getName();
        cv.surname = me.getSurname();
        cv.telephone = me.getPhone();
        cv.skype = me.getSkype();
        cv.skills = skillRepository.findAll();
        cv.educations = educationRepository.findAll();
        cv.languages = languageRepository.findAll();
        cv.experiences = experienceRepository.findAll();
        return cv;
    }

    public Education saveEducation(EducationModel educationModel) {
        Education education = new Education();
        education.setFromYear(educationModel.fromYear);
        education.setName(educationModel.name);
        education.setToYear(educationModel.toYear);
        education.setLevel(educationModel.level);
        education.setPresent(educationModel.present);
        return educationRepository.save(education);
    }

    public void removeEducation(Long id) {
        educationRepository.deleteById(id);
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public void removeSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public void removeExperience(Long id) {
        experienceRepository.deleteById(id);
    }

    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    public void removeLanguage(Long id) {
        languageRepository.deleteById(id);
    }

    public User saveMyDetails(User user) {
        return userRepository.save(user);
    }
}
