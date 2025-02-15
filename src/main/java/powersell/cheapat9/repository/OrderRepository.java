package powersell.cheapat9.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import powersell.cheapat9.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 특정 ID로 주문 조회
     */
    @Query("SELECT o FROM Order o JOIN FETCH o.item WHERE o.id = :id")
    Order findByIdWithItem(@Param("id") Long id);

    /**
     * 모든 주문 조회 (상품과 함께 페치 조인)
     */
    @Query("SELECT o FROM Order o JOIN FETCH o.item")
    List<Order> findAllWithItems();

    /**
     * 전화번호로 주문 조회
     */
    @Query("SELECT o FROM Order o JOIN FETCH o.item WHERE o.number = :number")
    List<Order> findAllByNumber(@Param("number") String number);
}
