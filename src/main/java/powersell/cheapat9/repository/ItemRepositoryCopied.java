//package powersell.cheapat9.repository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.LockModeType;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import powersell.cheapat9.domain.Item;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class ItemRepositoryCopied {
//
//    private final EntityManager em;
//
//    public void save(Item item) {
//        if (item.getId() == null) {
//            em.persist(item);
//        } else {
//            em.merge(item);
//        }
//    }
//
//    public Item findOne(Long id) { return em.find(Item.class, id, LockModeType.PESSIMISTIC_WRITE); }
//
//    public List<Item> findAll() {
//        return em.createQuery("select i from Item i", Item.class).getResultList();
//    }
//}
