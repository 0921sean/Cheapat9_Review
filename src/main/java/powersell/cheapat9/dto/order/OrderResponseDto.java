package powersell.cheapat9.dto.order;

import lombok.Getter;
import powersell.cheapat9.domain.Order;
import powersell.cheapat9.domain.OrderStatus;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private Long id;
    private String itemName;
    private int count;
    private int orderPrice;
    private String name;
    private String number;
    private String zipcode;
    private String address;
    private String dongho;
    private String pw;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.itemName = order.getItem().getName();
        this.count = order.getCount();
        this.orderPrice = order.getOrderPrice();
        this.name = order.getName();
        this.number = order.getNumber();
        this.zipcode = order.getZipcode();
        this.address = order.getAddress();
        this.dongho = order.getDongho();
        this.pw = order.getPw();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }
}
