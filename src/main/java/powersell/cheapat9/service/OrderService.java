package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.domain.Order;
import powersell.cheapat9.domain.OrderStatus;
import powersell.cheapat9.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    /**
     * 주문
     */
    @Transactional
    public Long saveOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 검색
     */
    @Transactional
    public List<Order> findOrders() { return orderRepository.findAll(); }
    public List<Order> findOrdersByNumber(String number) { return orderRepository.findAllByNumber(number); }

    public Order findOne(Long id) { return orderRepository.findOne(id); }

    /**
     * 주문 수정
     */
    @Transactional
    public void update(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findOne(id);
        order.setStatus(orderStatus);
    }

    /**
     * 주문 추가
     */
    @Transactional
    public Long createOrderAndModifyStock(Long itemId, Order order) {
        Item item = itemService.findOne(itemId);

        order.setItem(item);
        order.setOrderPrice(item.getPrice() * order.getCount());
        itemService.updateStock(item, order.getCount());

        orderRepository.save(order);
        return order.getId();
    }
}
