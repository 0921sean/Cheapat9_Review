package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.dto.item.ItemRequestDto;
import powersell.cheapat9.dto.item.ItemResponseDto;
import powersell.cheapat9.exception.NotEnoughStockException;
import powersell.cheapat9.repository.ItemRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
        Item item = Item.createItem(
                requestDto.getName(),
                requestDto.getOriginalPrice(),
                requestDto.getPrice(),
                requestDto.getStockQuantity(),
                LocalDateTime.parse(requestDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.parse(requestDto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateItem(Long id, ItemRequestDto requestDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.updateItem(
                requestDto.getName(),
                requestDto.getOriginalPrice(),
                requestDto.getPrice(),
                requestDto.getStockQuantity(),
                LocalDateTime.parse(requestDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.parse(requestDto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    /**
     * 상품 재고 수정
     */
    @Transactional
    public void updateItemStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾지 못했습니다."));

        item.decreaseStock(quantity);
    }

    /**
     * 전체 상품 조회
     */
    public List<ItemResponseDto> findAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 개별 상품 조회
     */
    public ItemResponseDto findItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾지 못했습니다."));
        return new ItemResponseDto(item);
    }

    /**
     * 개별 상품 조회 (OrderService에서 사용 위함)
     */
    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾지 못했습니다."));
    }
}