package powersell.cheapat9.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import powersell.cheapat9.domain.Order;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) { em.persist(order); }

    public Order findOne(Long id) { return em.find(Order.class, id); }

    public List<Order> findAll() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.item i", Order.class)
                .getResultList();
    }

    public List<Order> findAllByNumber(String number) {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.item" +
                        " where o.number = :number", Order.class)
                .setParameter("number", number)
                .getResultList();
    }
}
