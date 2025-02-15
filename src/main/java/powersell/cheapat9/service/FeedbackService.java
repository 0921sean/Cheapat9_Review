package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Feedback;
import powersell.cheapat9.dto.feedback.FeedbackRequestDto;
import powersell.cheapat9.dto.feedback.FeedbackResponseDto;
import powersell.cheapat9.repository.FeedbackRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    /**
     * 피드백 저장
     */
    @Transactional
    public Long saveFeedback(FeedbackRequestDto requestDto) {
        Feedback feedback = Feedback.createFeedback(requestDto.getContent());
        feedbackRepository.save(feedback);
        return feedback.getId();
    }

    /**
     * 모든 피드백 조회
     */
    public List<FeedbackResponseDto> findAllFeedbacks() {
        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackResponseDto::new)
                .collect(Collectors.toList());
    }
}
