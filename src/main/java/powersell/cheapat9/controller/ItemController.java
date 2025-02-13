package powersell.cheapat9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.form.ItemForm;
import powersell.cheapat9.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 전체 상품 조회
     */
    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        List<Item> items = itemService.findItems();
        List<ItemDto> result = items.stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form) {
        Item item = new Item();
        item.setName(form.getName());
        item.setOriginalPrice(form.getOriginalPrice());
        item.setPrice(form.getPrice());
        item.setDiscountRate((form.getOriginalPrice() - form.getPrice()) * 100 / form.getOriginalPrice());
        item.setStockQuantity(form.getStockQuantity());

        itemService.saveItem(item);
        return "redirect:/";
    }

    @PostMapping("/items/{itemId}/detail")
    public String itemDetailForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        model.addAttribute("item", item);
        return "items/detailItemForm";
    }
}
