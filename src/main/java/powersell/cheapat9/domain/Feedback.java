package powersell.cheapat9.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    private String content;

    /**
     * 피드백 생성 메서드
     */
    public static Feedback createFeedback(String content) {
        Feedback feedback = new Feedback();
        feedback.content = content;
        return feedback;
    }

    /**
     * 피드백 수정 메서드
     */
    public void updateFeedback(String content) {
        this.content = content;
    }
}
