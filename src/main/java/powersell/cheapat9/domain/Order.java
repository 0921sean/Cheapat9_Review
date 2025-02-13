package powersell.cheapat9.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
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

    private LocalDateTime orderDate;
}
