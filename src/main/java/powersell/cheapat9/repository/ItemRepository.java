package powersell.cheapat9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import powersell.cheapat9.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 특정 ID로 상품 조회 (LOCK 사용 - 동시성 제어)
    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Item findByIdWithLock(@Param("id") Long id, LockModeType lockModeType);

    // 모든 상품 조회 (페치 조인 추가 가능)
    List<Item> findAllItems();
}
