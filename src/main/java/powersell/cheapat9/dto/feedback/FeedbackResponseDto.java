package powersell.cheapat9.dto.feedback;

import lombok.Getter;
import powersell.cheapat9.domain.Feedback;

@Getter
public class FeedbackResponseDto {
    private Long id;
    private String content;

    public FeedbackResponseDto(Feedback feedback) {
        this.id = feedback.getId();
        this.content = feedback.getContent();
    }
}
