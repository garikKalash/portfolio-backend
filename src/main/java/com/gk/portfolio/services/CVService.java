package com.gk.portfolio.services;

import com.gk.portfolio.entities.*;
import com.gk.portfolio.models.EducationModel;
import com.gk.portfolio.models.ExperienceModel;
import com.gk.portfolio.models.LanguageModel;
import com.gk.portfolio.models.SkillModel;
import com.gk.portfolio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

import static java.lang.String.format;

@Service
public class CVService {
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    ExperienceRepository experienceRepository;

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

    public Education updateEducation(Long id, EducationModel educationModel) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Education with id %s not found.", id)));
        education.setFromYear(educationModel.fromYear);
        education.setName(educationModel.name);
        education.setToYear(educationModel.toYear);
        education.setLevel(educationModel.level);
        education.setPresent(educationModel.present);
        return educationRepository.save(education);

    }

    public Skill saveSkill(SkillModel skillModel) {
        Skill skill = new Skill();
        skill.setName(skillModel.name);
        skill.setExperienceInYear(skillModel.experience);
        return skillRepository.save(skill);
    }

    public void removeSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public Skill updateSkill(Long id, SkillModel skillModel) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Skill with id %s not found.", id)));
        skill.setName(skillModel.name);
        skill.setExperienceInYear(skillModel.experience);
        return skillRepository.save(skill);
    }

    public Experience saveExperience(ExperienceModel experienceModel) {
        Experience experience = new Experience();
        experience.setFromYear(experienceModel.fromYear);
        experience.setToYear(experienceModel.toYear);
        experience.setCompany(experienceModel.company);
        experience.setWorkRole(experienceModel.workRole);
        experience.setDescription(experienceModel.description);
        experience.setPresent(experienceModel.present);
        experience.setAvatarLink(experienceModel.avatarLink);
        return experienceRepository.save(experience);
    }

    public void removeExperience(Long id) {
        experienceRepository.deleteById(id);
    }

    public Experience updateExperience(Long id, ExperienceModel experienceModel) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Experience with id %s not found.", id)));
        experience.setFromYear(experienceModel.fromYear);
        experience.setToYear(experienceModel.toYear);
        experience.setCompany(experienceModel.company);
        experience.setWorkRole(experienceModel.workRole);
        experience.setDescription(experienceModel.description);
        experience.setPresent(experienceModel.present);
        experience.setAvatarLink(experienceModel.avatarLink);
        return experienceRepository.save(experience);
    }

    public Language saveLanguage(LanguageModel languageModel) {
        Language language = new Language();
        language.setLevel(languageModel.level);
        language.setName(languageModel.name);
        return languageRepository.save(language);
    }

    public void removeLanguage(Long id) {
        languageRepository.deleteById(id);
    }

    public Language updateLanguage(Long id, LanguageModel languageModel) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Language with id %s not found.", id)));
        language.setLevel(languageModel.level);
        language.setName(languageModel.name);
        return languageRepository.save(language);
    }


}
