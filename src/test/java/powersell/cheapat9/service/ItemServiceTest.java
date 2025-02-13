package powersell.cheapat9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.exception.NotEnoughStockException;
import powersell.cheapat9.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    /**
     * 1. 상품 저장 테스트
     */
    @Test
    public void saveItemTest() {
        //given
        Item item = new Item();
        item.setName("Egg");
        item.setOriginalPrice(4000);
        item.setPrice(800);
        item.setDiscountRate(80);
        item.setStockQuantity(20);
        item.setStartDate(LocalDateTime.now());
        item.setEndDate(LocalDateTime.now().plusDays(10));

        // when
        Long savedItemId = itemService.saveItem(item);
        Item savedItem = itemService.findOne(savedItemId);

        // then
        assertNotNull(savedItem);
        assertEquals("Egg", savedItem.getName());
    }

    /**
     * 2. 상품 조회 테스트
     */
    @Test
    public void findOneTest() {
        // given
        Item item = new Item();
        item.setName("CupRice");
        item.setOriginalPrice(5000);
        item.setPrice(1000);
        item.setStockQuantity(25);
        itemRepository.save(item);

        // when
        Item foundItem = itemService.findOne(item.getId());

        // then
        assertNotNull(foundItem);
        assertEquals("CupRice", foundItem.getName());
    }

    @Test
    public void findAllTest() {
        // given
        Item item1 = new Item();
        item1.setName("Bread");
        item1.setOriginalPrice(3000);
        item1.setPrice(2500);
        item1.setStockQuantity(50);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("Butter");
        item2.setOriginalPrice(7000);
        item2.setPrice(6500);
        item2.setStockQuantity(30);
        itemRepository.save(item2);

        // when
        List<Item> items = itemService.findItems();

        // then
        assertEquals(2, items.size());
    }

    /**
     * 3. 상품 수정 테스트
     */
    @Test
    public void updateItemTest() {
        // given
        Item item = new Item();
        item.setName("Cheese");
        item.setOriginalPrice(10000);
        item.setPrice(8000);
        item.setStockQuantity(10);
        item.setStartDate(LocalDateTime.now());
        item.setEndDate(LocalDateTime.now().plusDays(7));
        itemRepository.save(item);

        // when
        itemService.update(item.getId(), "Premium Cheese", 12000, 10000, 5,
                "2025-02-12 10:00:00", "2025-02-20 10:00:00");

        // then
        Item updatedItem = itemService.findOne(item.getId());
        assertEquals("Premium Cheese", updatedItem.getName());
        assertEquals(12000, updatedItem.getOriginalPrice());
        assertEquals(10000, updatedItem.getPrice());
        assertEquals(5, updatedItem.getStockQuantity());
    }

    /**
     * 4. 상품 재고 수정 테스트
     */
    @Test
    public void updateStockTest() {
        // given
        Item item = new Item();
        item.setName("Yogurt");
        item.setOriginalPrice(4500);
        item.setPrice(4000);
        item.setStockQuantity(20);
        itemRepository.save(item);

        // when
        itemService.updateStock(item, 5);

        // then
        Item updatedItem = itemService.findOne(item.getId());
        assertEquals(15, updatedItem.getStockQuantity());
    }

    /**
     * 5. 재고 부족 예외 테스트
     */
    @Test
    public void updateStock_throwsExceptionWhenNotEnoughStock() {
        // given
        Item item = new Item();
        item.setName("Chocolate");
        item.setOriginalPrice(5500);
        item.setPrice(5000);
        item.setStockQuantity(5);
        itemRepository.save(item);

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            itemService.updateStock(item, 10);  // 재고 부족 테스트
        });
    }
}
