package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.models.FeedbackModel
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.NotFoundException

class FeedbackServiceTest extends AppIntegrationTest {

    @Autowired
    FeedbackService feedbackService

    FeedbackModel model;
    @Before
    FeedbackModel createModel(){
       model = new FeedbackModel()
        model.name = 'author'
        model.position = 'engineer'
        model.durationInMans = 2
        model.text = 'excellent team'
        return model;
    }


    def "save feedback"() {
        when:
        def feedbackList = feedbackService.getAll()

        then:
        feedbackList.empty

        when:
        def saved = feedbackService.save(model)
        feedbackList = feedbackService.getAll()

        then:
        feedbackList.size() == 1
        saved.name == model.name
        saved.position == model.position
        saved.durationInMans == model.durationInMans
        saved.text == model.text

        cleanup:
        feedbackService.delete(saved.id)
    }

    def "update feedback"() {
        given:
        FeedbackModel model2 = new FeedbackModel()
        model2.name = 'author2'
        model2.position = 'leader engineer'
        model2.durationInMans = 3
        model2.text = 'excellent team'

        and:
        def saved = feedbackService.save(model)
        def invalidId = saved.id + 1

        when:
        def updated = feedbackService.update(saved.id, model2)

        then:
        updated.getId() == saved.getId()
        updated.getName() == model2.name
        updated.getPosition() == model2.position
        updated.getDurationInMans() == model2.durationInMans
        updated.getText() == model2.text

        when:
        feedbackService.update(invalidId, model2)

        then:
        thrown(NotFoundException)

        cleanup:
        feedbackService.delete(updated.getId())
    }

    def "delete feedback"() {

        def saved = feedbackService.save(model)

        when:
        def feedbacks = feedbackService.getAll()

        then:
        feedbacks.size() == 1

        when:
        feedbackService.delete(saved.id)
        feedbacks = feedbackService.getAll()

        then:
        feedbacks.empty

    }


}
