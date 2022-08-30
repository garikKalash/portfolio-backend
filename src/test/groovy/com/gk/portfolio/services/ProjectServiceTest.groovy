package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.models.ProjectModel
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.NotFoundException

class ProjectServiceTest extends AppIntegrationTest{

    @Autowired
    ProjectService projectService

    ProjectModel model

    @Before
    ProjectModel createProjectModel(){
        model = new ProjectModel()
        model.name = 'pro_name'
        model.description = 'shopping service'
        model.techDescription = 'java project'
        model.projectType = 'backend'
        return model
    }


    def "save project"() {

        when:
        def projects = projectService.getAll()

        then:
        projects.empty

        when:
        def saved = projectService.save(model)
        projects = projectService.getAll()

        then:
        saved.name == model.name
        saved.description == model.description
        saved.techDescription == model.techDescription
        saved.projectType == model.projectType

        and:
        projects.stream().anyMatch({ project -> project.name == model.name })

        cleanup:
        projectService.delete(saved.id)
    }

    def "update project"() {
        given:
        ProjectModel model2 = new ProjectModel()
        model2.name = 'pro_name2'
        model2.description = 'shopping service2'
        model2.techDescription = 'java project2'
        model2.projectType = 'backend2'

        and:
        def saved = projectService.save(model)
        def invalidId = saved.id + 1

        when:
        def updated = projectService.update(saved.getId(), model2)


        then:
        updated.getId() == saved.getId()
        updated.getName() == model2.name
        updated.getDescription() == model2.description
        updated.getTechDescription() == model2.techDescription
        updated.getProjectType() == model2.projectType

        when:
        projectService.update(invalidId, model2)

        then:
        thrown(NotFoundException)

        cleanup:
        projectService.delete(updated.getId())
    }

    def "delete project"() {
        def saved = projectService.save(model)

        when:
        def projects = projectService.getAll()

        then:
        projects.size() == 1

        when:
        projectService.delete(saved.id)
        projects = projectService.getAll()

        then:
        projects.empty

    }



}
