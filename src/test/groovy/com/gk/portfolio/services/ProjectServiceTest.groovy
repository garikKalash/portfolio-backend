package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.models.ProjectModel
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.NotFoundException

class ProjectServiceTest extends AppIntegrationTest{

    @Autowired
    ProjectService projectService

    ProjectModel model = new ProjectModel()

    @Before
    ProjectModel createProjectModel(){
        model.name = 'name'
        model.description = 'shopping service'
        model.techDescription = 'java project'
        model.projectType = 'backend'
        return model
    }


    def "save project"() {

        when:
        def saved = projectService.save(model)
        def projects = projectService.getAll()

        then:
        saved.name == model.name
        saved.description == model.description
        saved.techDescription == model.techDescription
        saved.projectType == model.projectType

        and:
        projects.stream().map({ project -> project.id == saved.id }).any()

        cleanup:
        projectService.delete(saved.id)
    }

    def "update project"() {
        given:
        ProjectModel model2 = new ProjectModel()
        model2.name = 'name2'
        model2.description = 'shopping service2'
        model2.techDescription = 'java project2'
        model2.projectType = 'backend2'

        and:
        def saved = projectService.save(model)
        def invalidId = saved.id + 1

        when:
        def updated = projectService.update((saved.id), model2)


        then:
        updated.id == saved.id
        updated.name == model2.name
        updated.description == model2.description
        updated.techDescription == model2.techDescription
        updated.projectType == model2.projectType

        when:
        projectService.update(invalidId, model2)

        then:
        thrown(NotFoundException)

        cleanup:
        projectService.delete(updated.getId())
    }

    def "delete project"() {

        when:
        def saved = projectService.save(model)
        def projects = projectService.getAll()

        then:
        projects.stream().map({ project -> project.id == saved.id }).any()

        when:
        projectService.delete(saved.id)
        projects = projectService.getAll()

        then:
        !projects.stream().map({ project -> project.id == saved.id }).any()

    }



}
