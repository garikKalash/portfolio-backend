package com.gk.portfolio.controllers;

import com.gk.portfolio.entities.Feedback;
import com.gk.portfolio.models.FeedbackModel;
import com.gk.portfolio.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@Validated
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/get")
    public List<Feedback> getAll() {
       return feedbackService.getAll();
    }

    @PostMapping ("/create")
    public Feedback createFeedback (@Valid @RequestBody FeedbackModel feedbackModel){
        return feedbackService.save(feedbackModel);
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('me')")
    public void delete (@PathVariable ("id") Long id){
        feedbackService.delete(id);
    }

    @PutMapping ("/{id}")
    @PreAuthorize("hasRole('me')")
    public Feedback update (@PathVariable ("id") Long id, @Valid @RequestBody FeedbackModel feedbackModel){
        return feedbackService.update(id, feedbackModel);
    }


}
