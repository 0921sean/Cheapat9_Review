package powersell.cheapat9.dto.feedback;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackRequestDto {
    private String content;

    public FeedbackRequestDto(String content) {
        this.content = content;
    }
}
