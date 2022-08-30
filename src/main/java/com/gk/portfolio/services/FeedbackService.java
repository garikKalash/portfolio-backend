package com.gk.portfolio.services;

import com.gk.portfolio.entities.Feedback;
import com.gk.portfolio.models.FeedbackModel;
import com.gk.portfolio.repositories.FeedbackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static java.lang.String.format;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    public Feedback save(FeedbackModel feedbackModel) {
        Feedback feedback = new Feedback();
        feedback.setName(feedbackModel.name);
        feedback.setDurationInMonths(feedbackModel.duration);
        feedback.setPosition(feedbackModel.position);
        feedback.setText(feedbackModel.text);
        feedback.setEmail(feedbackModel.email);
        return feedbackRepository.save(feedback);
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getAll (){
        return feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Feedback update(Long id, FeedbackModel feedbackModel){
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Feedback with id %s not found.", id)));
        feedback.setName(feedbackModel.name);
        feedback.setDurationInMonths(feedbackModel.duration);
        feedback.setPosition(feedbackModel.position);
        feedback.setText(feedbackModel.text);
        feedback.setEmail(feedbackModel.email);
        return  feedbackRepository.save(feedback);

    }


}
