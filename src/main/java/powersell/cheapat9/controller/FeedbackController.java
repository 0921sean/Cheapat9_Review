package powersell.cheapat9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powersell.cheapat9.domain.Feedback;
import powersell.cheapat9.dto.feedback.FeedbackRequestDto;
import powersell.cheapat9.dto.feedback.FeedbackResponseDto;
import powersell.cheapat9.service.FeedbackService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * 피드백 저장
     */
    @PostMapping
    public ResponseEntity<Long> saveFeedback(@RequestBody @Valid FeedbackRequestDto requestDto) {
        Long feedbackId = feedbackService.saveFeedback(requestDto);
        return ResponseEntity.ok(feedbackId);
    }

    /**
     * 전체 피드백 조회 (관리자용)
     */
    @GetMapping("/admin")
    public ResponseEntity<List<FeedbackResponseDto>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.findAllFeedbacks());
    }
}
