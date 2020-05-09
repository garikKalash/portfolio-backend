package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.entities.Experience
import com.gk.portfolio.entities.Skill
import com.gk.portfolio.models.EducationModel
import com.gk.portfolio.models.LanguageModel
import org.springframework.beans.factory.annotation.Autowired

class CVServiceITest extends AppIntegrationTest {
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
        def cv = cvService.cv

        then:
        cv.educations.empty

        when:
        def saved = cvService.saveEducation(model)
        cv = cvService.getCv()

        then:
        saved.name == model.name
        saved.fromYear == model.fromYear
        saved.toYear == model.toYear
        saved.level == model.level

        and:
        cv.educations.stream().anyMatch({ ed -> ed.name == model.name })

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
        cv.educations.size() == 1

        when:
        cvService.removeEducation(saved.id)
        cv = cvService.cv

        then:
        cv.educations.empty

    }

    
    def saveSkill() {
        given:
        Skill model = new Skill()
        model.name = 'edu_name'
        model.experienceInYear = 4.5

        when:
        def cv = cvService.cv

        then:
        cv.skills.empty

        when:
        def saved = cvService.saveSkill(model)
        cv = cvService.getCv()

        then:
        saved.name == model.name
        saved.experienceInYear == model.experienceInYear

        and:
        cv.skills.stream().anyMatch({ ed -> ed.name == model.name })

        cleanup:
        cvService.removeSkill(saved.id)
    }

    
    def removeSkill() {
        given:
        Skill model = new Skill()
        model.name = 'edu_name'
        model.experienceInYear = 4.5

        and:
        def saved = cvService.saveSkill(model)

        when:
        def cv = cvService.cv

        then:
        cv.skills.size() == 1

        when:
        cvService.removeSkill(saved.id)
        cv = cvService.cv

        then:
        cv.skills.empty
    }

    
    def saveExperience() {
        given:
        Experience model = new Experience()
        model.company = 'company_name'
        model.fromYear = 2000
        model.toYear = 2020
        model.description = 'company_desc'
        model.workRole = 'company_role'

        when:
        def cv = cvService.cv

        then:
        cv.experiences.empty

        when:
        def saved = cvService.saveExperience(model)
        cv = cvService.getCv()

        then:
        saved.id
        saved.company == model.company
        saved.fromYear == model.fromYear
        saved.toYear == model.toYear
        saved.description == model.description

        and:
        cv.experiences.stream().anyMatch({ ed -> ed.company == model.company })

        cleanup:
        cvService.removeExperience(saved.id)
    }

    
    def removeExperience() {
        given:
        Experience model = new Experience()
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
        cv.experiences.size() == 1

        when:
        cvService.removeExperience(saved.id)
        cv = cvService.cv

        then:
        cv.experiences.empty
    }

    
    def saveLanguage() {
        given:
        LanguageModel model = new LanguageModel()
        model.name = 'lang_name'
        model.level = 'lang_levl'

        when:
        def cv = cvService.cv

        then:
        cv.languages.empty

        when:
        def saved = cvService.saveLanguage(model)
        cv = cvService.getCv()

        then:
        saved.getName() == model.name
        saved.getLevel() == model.level

        and:
        cv.languages.stream().anyMatch({ ed -> ed.getName() == model.name })

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
        cv.languages.size() == 1

        when:
        cvService.removeLanguage(saved.id)
        cv  = cvService.cv

        then:
        cv.languages.empty
    }
}
