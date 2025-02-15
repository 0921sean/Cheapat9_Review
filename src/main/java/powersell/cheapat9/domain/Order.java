package powersell.cheapat9.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;  // 주문량
    private int orderPrice;

    private String name;
    private String number;  // 전화번호 (10, 11 digits)
    private String zipcode;
    private String address;
    private String dongho;
    private String pw;  // 비밀번호 (4자리)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    /**
     * 🔹 주문 생성 메서드
     */
    public static Order createOrder(Item item, int count, String name, String number,
                                    String zipcode, String address, String dongho, String pw) {
        Order order = new Order();
        order.item = item;
        order.count = count;
        order.orderPrice = item.getPrice() * count;  // 총 주문 가격 = 개별 가격 * 주문량
        order.name = name;
        order.number = number;
        order.zipcode = zipcode;
        order.address = address;
        order.dongho = dongho;
        order.pw = pw;
        order.status = OrderStatus.WAITING;
        order.orderDate = LocalDateTime.now();
        return order;
    }

    /**
     * 🔹 주문 상태 변경 메서드
     */
    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * 🔹 주문 수정 메서드
     */
    public void updateOrder(int count, String name, String number,
                            String zipcode, String address, String dongho, String pw) {
        this.count = count;
        this.orderPrice = this.item.getPrice() * count; // 주문 수량 변경 시 주문 가격도 변경
        this.name = name;
        this.number = number;
        this.zipcode = zipcode;
        this.address = address;
        this.dongho = dongho;
        this.pw = pw;
    }
}
