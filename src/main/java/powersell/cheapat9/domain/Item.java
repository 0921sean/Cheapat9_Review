package powersell.cheapat9.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import powersell.cheapat9.exception.NotEnoughStockException;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 보호
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int originalPrice;
    private int price;  // 할인된 가격
    private int stockQuantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    /**
     * 상품 생성 메서드
     */
    public static Item createItem(String name, int originalPrice, int price, int stockQuantity,
                                  LocalDateTime startDate, LocalDateTime endDate) {
        Item item = new Item();
        item.name = name;
        item.originalPrice = originalPrice;
        item.price = price;
        item.stockQuantity = stockQuantity;
        item.startDate = startDate;
        item.endDate = endDate;
        return item;
    }

    /**
     * 상품 수정 메서드
     */
    public void updateItem(String name, int originalPrice, int price, int stockQuantity,
                           LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 재고 수정 메서드
     */
    public void decreaseStock(int quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }
}
