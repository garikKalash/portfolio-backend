package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.models.FeedbackModel
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.NotFoundException

class FeedbackServiceTest extends AppIntegrationTest {

    @Autowired
    FeedbackService feedbackService

    FeedbackModel model = new FeedbackModel();

    @Before
    FeedbackModel createModel(){
        model.name = 'author'
        model.position = 'engineer'
        model.duration = 2
        model.text = 'excellent team'
        model.email = 'author@gmail.com'
        return model;
    }

    def "save feedback"() {
        when:

        def saved = feedbackService.save(model)
        def feedbackList = feedbackService.getAll()

        then:
        saved.name == model.name
        saved.position == model.position
        saved.durationInMonths == model.duration
        saved.text == model.text
        saved.email == model.email
        feedbackList.stream().map({ fdb -> fdb.id == saved.id }).any()

        cleanup:
        feedbackService.delete(saved.id)
    }

    def "update feedback"() {
        given:
        FeedbackModel model2 = new FeedbackModel()
        model2.name = 'author2'
        model2.position = 'leader engineer'
        model2.duration = 3
        model2.text = 'excellent team'
        model2.email = 'author2@gmail.com'

        and:
        def saved = feedbackService.save(model)
        def invalidId = saved.id + 1

        when:
        def updated = feedbackService.update(saved.id, model2)

        then:
        updated.id == saved.getId()
        updated.name == model2.name
        updated.position == model2.position
        updated.durationInMonths == model2.duration
        updated.text == model2.text
        updated.email == model2.email

        when:
        feedbackService.update(invalidId, model2)

        then:
        thrown(NotFoundException)

        cleanup:
        feedbackService.delete(updated.id)
    }

    def "delete feedback"() {

        def saved = feedbackService.save(model)

        when:
        def feedbacks = feedbackService.getAll()

        then:
        feedbacks.stream().map({ fdb -> fdb.id == saved.id }).any()

        when:
        feedbackService.delete(saved.id)
        feedbacks = feedbackService.getAll()

        then:
        !feedbacks.stream().map({ fdb -> fdb.id == saved.id }).any()


    }


}
