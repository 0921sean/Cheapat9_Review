package powersell.cheapat9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.dto.feedback.FeedbackRequestDto;
import powersell.cheapat9.dto.feedback.FeedbackResponseDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "admin.password=test1234")
@Transactional
public class FeedbackServiceTest {

    @Autowired private FeedbackService feedbackService;

    /**
     * 1. 피드백 저장 테스트
     */
    @Test
    public void saveFeedbackTest() {
        // given
        FeedbackRequestDto requestDto = new FeedbackRequestDto("This is a test feedback.");

        // when
        Long feedbackId = feedbackService.saveFeedback(requestDto);

        // then
        List<FeedbackResponseDto> feedbacks = feedbackService.findAllFeedbacks();
        assertTrue(feedbacks.stream().anyMatch(f -> f.getId().equals(feedbackId)));
        assertEquals("This is a test feedback.", feedbacks.stream()
                .filter(f -> f.getId().equals(feedbackId))
                .findFirst()
                .orElseThrow()
                .getContent());
    }

    /**
     * 2. 전체 피드백 조회 테스트
     */
    @Test
    public void findAllFeedbacksTest() {
        // given
        FeedbackRequestDto requestDto1 = new FeedbackRequestDto("Feedback 1");
        FeedbackRequestDto requestDto2 = new FeedbackRequestDto("Feedback 2");

        feedbackService.saveFeedback(requestDto1);
        feedbackService.saveFeedback(requestDto2);

        // when
        List<FeedbackResponseDto> feedbacks = feedbackService.findAllFeedbacks();

        // then
        assertEquals(2, feedbacks.size());
        assertEquals("Feedback 1", feedbacks.get(0).getContent());
        assertEquals("Feedback 2", feedbacks.get(1).getContent());
    }
}
