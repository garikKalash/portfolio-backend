package com.gk.portfolio.controllers;

import com.gk.portfolio.config.ModelMapperConfig;
import com.gk.portfolio.dto.FeedbackDTO;
import com.gk.portfolio.entities.Feedback;
import com.gk.portfolio.models.FeedbackModel;
import com.gk.portfolio.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feedback")
@Validated
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    ModelMapperConfig modelMapper;

    @GetMapping("/get")
    public List<FeedbackDTO> getAll() {
        List<Feedback> feedbacks = feedbackService.getAll();
        return feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public FeedbackDTO createFeedback(@Valid @RequestBody FeedbackModel feedbackModel) {
        Feedback feedback = feedbackService.save(feedbackModel);
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public void delete(@PathVariable("id") Long id) {
        feedbackService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('me')")
    public FeedbackDTO update(@PathVariable("id") Long id, @Valid @RequestBody FeedbackModel feedbackModel) {
        Feedback feedback = feedbackService.update(id, feedbackModel);
        return modelMapper.map(feedback, FeedbackDTO.class);
    }


}
