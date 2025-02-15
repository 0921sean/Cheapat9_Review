package powersell.cheapat9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powersell.cheapat9.dto.item.ItemRequestDto;
import powersell.cheapat9.dto.item.ItemResponseDto;
import powersell.cheapat9.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 전체 상품 조회
     */
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(itemService.findAllItems());
    }

    /**
     * 개별 상품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findItem(id));
    }

    /**
     * 상품 추가
     */
    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody @Valid ItemRequestDto requestDto) {
        return ResponseEntity.ok(itemService.saveItem(requestDto));
    }

    /**
     * 상품 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody @Valid ItemRequestDto requestDto) {
        itemService.updateItem(id, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 상품 재고 수정
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateItemStock(@PathVariable Long id, @RequestParam int quantity) {
        itemService.updateItemStock(id, quantity);
        return ResponseEntity.ok().build();
    }
}
