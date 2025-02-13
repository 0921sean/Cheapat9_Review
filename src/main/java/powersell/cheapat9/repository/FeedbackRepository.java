package powersell.cheapat9.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import powersell.cheapat9.domain.Feedback;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedbackRepository {

    private final EntityManager em;

    public void save(Feedback feedback) { em.persist(feedback); }

    public List<Feedback> findAll() {
        return em.createQuery("select f from Feedback f", Feedback.class)
                .getResultList();
    }
}
