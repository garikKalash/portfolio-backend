package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.entities.Experience
import com.gk.portfolio.entities.Skill
import com.gk.portfolio.models.EducationModel
import com.gk.portfolio.models.ExperienceModel
import com.gk.portfolio.models.LanguageModel
import com.gk.portfolio.models.SkillModel
import org.springframework.beans.factory.annotation.Autowired

class CVServiceTest extends AppIntegrationTest {

    @Autowired
    CVService cvService

    def "get cv"() {
        when:
        def cv = cvService.getCv()

        then:
        cv.name == 'Garik'
        cv.surname == 'Kalashyan'
        cv.email == 'garik.kalash@gmail.com'
    }

    def "save education"() {
        given:
        EducationModel model = new EducationModel()
        model.name = 'edu_name'
        model.fromYear = 2000
        model.toYear = 2020
        model.level = 'edu_levl'

        when:
        def saved = cvService.saveEducation(model)
        def cv = cvService.cv

        then:
        saved.name == model.name
        saved.fromYear == model.fromYear
        saved.toYear == model.toYear
        saved.level == model.level

        and:
        cv.educations.stream().map({ ed -> ed.id == saved.id }).any()

        cleanup:
        cvService.removeEducation(saved.id)
    }


    def removeEducation() {
        given:
        EducationModel model = new EducationModel()
        model.name = 'edu_name'
        model.fromYear = 2000
        model.toYear = 2020
        model.level = 'edu_levl'

        and:
        def saved = cvService.saveEducation(model)

        when:
        def cv = cvService.cv

        then:
        cv.educations.stream().map({ ed -> ed.id == saved.id }).any()

        when:
        cvService.removeEducation(saved.id)
        cv = cvService.cv

        then:
        !cv.educations.stream().map({ ed -> ed.id == saved.id }).any()

    }


    def saveSkill() {
        given:
        SkillModel model = new SkillModel()
        model.name = 'edu_name'
        model.experience = 4.5

        when:
        def saved = cvService.saveSkill(model)
        def cv = cvService.getCv()

        then:
        saved.name == model.name
        saved.experienceInYear == model.experience

        and:
        cv.skills.stream().map({ skill -> skill.id == saved.id }).any()

        cleanup:
        cvService.removeSkill(saved.id)
    }


    def removeSkill() {
        given:
        SkillModel model = new SkillModel()
        model.name = 'edu_name'
        model.experience = 4.5

        and:
        def saved = cvService.saveSkill(model)

        when:
        def cv = cvService.cv

        then:
        cv.skills.stream().map({ skill -> skill.id == saved.id }).any()

        when:
        cvService.removeSkill(saved.id)
        cv = cvService.cv

        then:
        !cv.skills.stream().map({ skill -> skill.id == saved.id }).any()

    }


    def saveExperience() {
        given:
        ExperienceModel model = new ExperienceModel()
        model.company = 'company_name'
        model.fromYear = 2000
        model.toYear = 2020
        model.description = 'company_desc'
        model.workRole = 'company_role'

        when:
        def saved = cvService.saveExperience(model)
        def  cv = cvService.getCv()

        then:
        saved.company == model.company
        saved.fromYear == model.fromYear
        saved.toYear == model.toYear
        saved.description == model.description

        and:
        cv.experiences.stream().map({ exp -> exp.id == saved.id }).any()

        cleanup:
        cvService.removeExperience(saved.id)
    }


    def removeExperience() {
        given:
        ExperienceModel model = new ExperienceModel()
        model.company = 'company_name'
        model.fromYear = 2000
        model.toYear = 2020
        model.description = 'company_levl'
        model.workRole = 'company_role'

        and:
        def saved = cvService.saveExperience(model)

        when:
        def cv = cvService.cv
        then:
        cv.experiences.stream().map({ exp -> exp.id == saved.id }).any()

        when:
        cvService.removeExperience(saved.id)
        cv = cvService.cv

        then:
        !cv.experiences.stream().map({ exp -> exp.id == saved.id }).any()
    }


    def saveLanguage() {
        given:
        LanguageModel model = new LanguageModel()
        model.name = 'lang_name'
        model.level = 'lang_levl'

        when:
        def saved = cvService.saveLanguage(model)
        def cv = cvService.getCv()

        then:
        saved.getName() == model.name
        saved.getLevel() == model.level

        and:
        cv.languages.stream().map({ lng -> lng.id == saved.id }).any()

        cleanup:
        cvService.removeLanguage(saved.id)
    }


    def removeLanguage() {
        given:
        LanguageModel model = new LanguageModel()
        model.name = 'lang_name'
        model.level = 'lang_levl'

        and:
        def saved = cvService.saveLanguage(model)

        when:
        def cv = cvService.cv

        then:
        cv.languages.stream().map({ lng -> lng.id == saved.id }).any()

        when:
        cvService.removeLanguage(saved.id)
        cv = cvService.cv

        then:
        !cv.languages.stream().map({ lng -> lng.id == saved.id }).any()
    }
}
