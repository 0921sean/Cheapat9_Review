package powersell.cheapat9.dto;

import lombok.Data;
import powersell.cheapat9.domain.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ItemDto {

    private Long itemId;
    private String name;
    private int originalPrice;
    private int price;
    private int discountRate;
    private int stockQuantity;
    private String startDate;
    private String endDate;
    private boolean eventIng;

    public ItemDto(Item item) {
        itemId = item.getId();
        name = item.getName();
        originalPrice = item.getOriginalPrice();
        price = item.getPrice();
        discountRate = item.getDiscountRate();
        stockQuantity = item.getStockQuantity();
        startDate = item.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        endDate = item.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        LocalDateTime today = LocalDateTime.now();
        eventIng = !today.isBefore(item.getStartDate()) && !today.isAfter(item.getEndDate());
    }

}
