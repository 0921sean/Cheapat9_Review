package powersell.cheapat9.dto.item;

import lombok.Getter;
import powersell.cheapat9.domain.Item;

@Getter
public class ItemResponseDto {
    private Long id;
    private String name;
    private int originalPrice;
    private int price;
    private int stockQuantity;
    private String startDate;
    private String endDate;
    private int discountRate;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.originalPrice = item.getOriginalPrice();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.startDate = item.getStartDate().toString();
        this.endDate = item.getEndDate().toString();
        this.discountRate = calculateDiscountRate(item.getOriginalPrice(), item.getPrice());
    }

    private int calculateDiscountRate(int originalPrice, int price) {
        if (originalPrice == 0) return 0;
        return ((originalPrice - price) * 100) / originalPrice;
    }
}
