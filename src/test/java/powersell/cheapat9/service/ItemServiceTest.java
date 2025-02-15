package powersell.cheapat9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.dto.item.ItemRequestDto;
import powersell.cheapat9.dto.item.ItemResponseDto;
import powersell.cheapat9.exception.NotEnoughStockException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "admin.password=test1234")
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;

    /**
     * 1. 상품 저장 테스트
     */
    @Test
    public void saveItemTest() {
        // given
        ItemRequestDto requestDto = createItemRequestDto();

        // when
        Long savedItemId = itemService.saveItem(requestDto);

        // then
        ItemResponseDto foundItem = itemService.findItem(savedItemId);
        assertNotNull(foundItem);
        assertEquals("Test Item", foundItem.getName());
        assertEquals(10000, foundItem.getOriginalPrice());
        assertEquals(8000, foundItem.getPrice());
    }

    /**
     * 2. 상품 수정 테스트
     */
    @Test
    public void updateItemTest() {
        // given
        Long savedItemId = itemService.saveItem(createItemRequestDto());

        ItemRequestDto updateRequestDto = new ItemRequestDto();
        updateRequestDto.setName("Updated Test Item");
        updateRequestDto.setOriginalPrice(12000);
        updateRequestDto.setPrice(10000);
        updateRequestDto.setStockQuantity(5);
        updateRequestDto.setStartDate("2025-02-15 21:00:00");
        updateRequestDto.setEndDate("2025-02-15 23:00:00");

        // when
        itemService.updateItem(savedItemId, updateRequestDto);

        // then
        ItemResponseDto updatedItem = itemService.findItem(savedItemId);
        assertEquals("Updated Test Item", updatedItem.getName());
        assertEquals(12000, updatedItem.getOriginalPrice());
        assertEquals(10000, updatedItem.getPrice());
        assertEquals(5, updatedItem.getStockQuantity());

        // LocalDateTime을 포맷 변경 없이 그대로 가져오기
        String actualStartDate = updatedItem.getStartDate().replace("T", " ") + ":00";
        String actualEndDate = updatedItem.getEndDate().replace("T", " ") + ":00";

        // 기대하는 날짜 값
        String expectedStartDate = "2025-02-15 21:00:00";
        String expectedEndDate = "2025-02-15 23:00:00";

        // 문자열 그대로 비교
        assertEquals(expectedStartDate, actualStartDate);
        assertEquals(expectedEndDate, actualEndDate);
    }

    /**
     * 3. 상품 조회 테스트
     */
    @Test
    public void findItemTest() {
        // given
        Long savedItemId = itemService.saveItem(createItemRequestDto());

        // when
        ItemResponseDto foundItem = itemService.findItem(savedItemId);

        // then
        assertNotNull(foundItem);
        assertEquals("Test Item", foundItem.getName());
        assertEquals(10000, foundItem.getOriginalPrice());
        assertEquals(8000, foundItem.getPrice());
    }

    /**
     * 4. 전체 상품 조회 테스트
     */
    @Test
    public void findAllItemsTest() {
        // given
        itemService.saveItem(createItemRequestDto("Item 1"));
        itemService.saveItem(createItemRequestDto("Item 2"));

        // when
        List<ItemResponseDto> items = itemService.findAllItems();

        // then
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Item 2", items.get(1).getName());
    }

    /**
     * 5. 상품 재고 수정 테스트
     */
    @Test
    public void updateItemStockTest() {
        // given
        Long savedItemId = itemService.saveItem(createItemRequestDto());

        // when
        itemService.updateItemStock(savedItemId, 3);
        ItemResponseDto updatedItem = itemService.findItem(savedItemId);

        // then
        assertEquals(7, updatedItem.getStockQuantity());
    }

    /**
     * 6. 재고 부족 예외 테스트
     */
    @Test
    public void updateItemStock_throwsExceptionWhenNotEnoughStock() {
        // given
        Long savedItemId = itemService.saveItem(createItemRequestDto());

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            itemService.updateItemStock(savedItemId, 15);  // 재고 부족 테스트
        });
    }

    /**
     * 테스트에서 중복되는 ItemRequestDto 생성 메서드
     */
    private ItemRequestDto createItemRequestDto() {
        return createItemRequestDto("Test Item");
    }

    private ItemRequestDto createItemRequestDto(String name) {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setName(name);
        requestDto.setOriginalPrice(10000);
        requestDto.setPrice(8000);
        requestDto.setStockQuantity(10);
        requestDto.setStartDate("2025-02-12 21:00:00");
        requestDto.setEndDate("2025-02-12 23:00:00");
        return requestDto;
    }
}
