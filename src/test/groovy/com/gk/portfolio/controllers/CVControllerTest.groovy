package com.gk.portfolio.controllers

import com.gk.portfolio.AppIntegrationTest

import com.gk.portfolio.models.SkillModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser

import javax.validation.ConstraintViolationException

@WithMockUser(username = "usertest", password = "password", roles = "me")
class CVControllerTest extends AppIntegrationTest {
    @Autowired
    CVController cvController

    def "Cv"() {
        when:
        def cv = cvController.cv()

        then:
        cv.name == 'Garik'
    }

    def "create skill"() {
        given:
        SkillModel model = new SkillModel()
        model.name = "skill_name"
        model.experience = 4.5

        when:
        def saved = cvController.createSkill(model)

        then:
        saved.name == model.name
        saved.experienceInYear == model.experience

        when: "blank name"
        model.name = "               "
        cvController.createSkill(model)

        then:
        thrown(ConstraintViolationException)

        cleanup:
        cvController.removeSkill(saved.id)

    }
}
