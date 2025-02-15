package powersell.cheapat9.dto.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemRequestDto {
    private String name;
    private int originalPrice;
    private int price;
    private int stockQuantity;
    private String startDate;
    private String endDate;
}
