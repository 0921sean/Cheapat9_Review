package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Feedback;
import powersell.cheapat9.repository.FeedbackRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional
    public Long saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
        return feedback.getId();
    }

    public List<Feedback> findFeedbacks() { return feedbackRepository.findAll(); }
}
