package powersell.cheapat9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import powersell.cheapat9.domain.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
