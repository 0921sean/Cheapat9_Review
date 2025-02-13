package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.exception.NotEnoughStockException;
import powersell.cheapat9.repository.ItemRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long saveItem(ItemRequestDto requestDto) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 상품 검색
     */
    public List<Item> findItems() { return itemRepository.findAll(); }

    public Item findOne(Long id) { return itemRepository.findOne(id); }

    /**
     * 상품 수정
     */
    @Transactional
    public void update(Long id, String name, int originalPrice, int price, int stockQuantity, String startDate, String endDate) {
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setOriginalPrice(originalPrice);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        LocalDateTime start = LocalDateTime.parse(startDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setStartDate(start);
        LocalDateTime end = LocalDateTime.parse(endDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setEndDate(end);
    }

    /**
     * 상품 재고 수정
     */
    @Transactional
    public void updateStock(Item item, int quantity) {
        if (item.getStockQuantity() - quantity < 0) {
            throw new NotEnoughStockException("Error: Not Enough Stock");
        }
        int changedQuantity = item.getStockQuantity() - quantity;
        item.setStockQuantity(changedQuantity);
    }
}
