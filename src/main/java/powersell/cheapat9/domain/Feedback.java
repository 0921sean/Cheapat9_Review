package powersell.cheapat9.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Feedback {

    @Id @GeneratedValue
    @Column(name = "feedback_id")
    private Long id;

    private String content;
}
