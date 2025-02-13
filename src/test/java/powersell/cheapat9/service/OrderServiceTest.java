package powersell.cheapat9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.domain.Order;
import powersell.cheapat9.repository.ItemRepository;
import powersell.cheapat9.repository.OrderRepository;
import powersell.cheapat9.domain.OrderStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ItemService itemService;
    @Autowired private ItemRepository itemRepository;

    /**
     * 1. 주문 저장 테스트
     */
    @Test
    public void saveOrderTest() {
        // given
        Item item = new Item();
        item.setName("Water");
        item.setOriginalPrice(3000);
        item.setPrice(600);
        item.setStockQuantity(30);
        itemRepository.save(item);

        Order order = new Order();
        order.setItem(item);
        order.setCount(2);
        order.setOrderPrice(5000);
        order.setNumber("01012345678");
        order.setZipcode("12345");
        order.setAddress("Seoul, Korea");
        order.setDongho("101-202");
        order.setPw("1234");
        order.setStatus(OrderStatus.WAITING);

        // when
        Long orderId = orderService.saveOrder(order);

        // then
        Order savedOrder = orderService.findOne(orderId);
        assertNotNull(savedOrder);
        assertEquals(OrderStatus.WAITING, savedOrder.getStatus());
    }

    /**
     * 2. 주문 검색 테스트
     */
    @Test
    public void findOrdersTest() {
        // given
        Item item = new Item();
        item.setName("Water");
        item.setOriginalPrice(3000);
        item.setPrice(600);
        item.setStockQuantity(30);
        itemRepository.save(item);

        Order order1 = new Order();
        order1.setNumber("01098765432");
        order1.setStatus(OrderStatus.ARRIVED);
        order1.setItem(item);  // 필수 필드 설정
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setNumber("01012345678");
        order2.setStatus(OrderStatus.DELIVERING);
        order2.setItem(item);  // 필수 필드 설정
        orderRepository.save(order2);

        // when
        List<Order> orders = orderService.findOrders();
        System.out.println("주문 개수: " + orders.size());

        // then
        assertTrue(orders.size() >= 2);
    }

    @Test
    public void findOrdersByNumberTest() {
        // given
        Item item = new Item();
        item.setName("Water");
        item.setOriginalPrice(3000);
        item.setPrice(600);
        item.setStockQuantity(30);
        itemRepository.save(item);

        Order order1 = new Order();
        order1.setNumber("01077777777");
        order1.setStatus(OrderStatus.WAITING);
        order1.setItem(item);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setNumber("01087654321");
        order2.setStatus(OrderStatus.DELIVERING);
        order2.setItem(item);
        orderRepository.save(order2);

        // when
        List<Order> result = orderService.findOrdersByNumber("01077777777");
        System.out.println(result.size());

        // then
        assertEquals(1, result.size());
        assertEquals("01077777777", result.get(0).getNumber());
    }

    /**
     * 3. 주문 상태 수정 테스트
     */
    @Test
    public void updateOrderStatusTest() {
        // given
        Item item = new Item();
        item.setName("Water");
        item.setOriginalPrice(3000);
        item.setPrice(600);
        item.setStockQuantity(30);
        itemRepository.save(item);

        Order order = new Order();
        order.setNumber("01088889999");
        order.setStatus(OrderStatus.WAITING);
        order.setItem(item);
        orderRepository.save(order);

        // when
        orderService.update(order.getId(), OrderStatus.DELIVERING);

        // then
        Order updatedOrder = orderService.findOne(order.getId());
        assertEquals(OrderStatus.DELIVERING, updatedOrder.getStatus());
    }

    /**
     * 4. 주문 추가 및 재고 수정 테스트
     */
    @Test
    public void createOrderAndModifyStockTest() {
        // given
        Item item = new Item();
        item.setName("Water");
        item.setOriginalPrice(3000);
        item.setPrice(600);
        item.setStockQuantity(30);
        Long itemId = itemService.saveItem(item);  // 아이템 저장

        Order order = new Order();
        order.setNumber("01099998888");
        order.setCount(10);
        order.setStatus(OrderStatus.WAITING);
        order.setItem(item);

        // when
        Long orderId = orderService.createOrderAndModifyStock(itemId, order);

        // then
        Order savedOrder = orderService.findOne(orderId);
        assertNotNull(savedOrder);
        assertEquals("01099998888", savedOrder.getNumber());
        assertEquals(OrderStatus.WAITING, savedOrder.getStatus());
        assertEquals(10, savedOrder.getCount());

        // 재고 감소 검증
        Item updatedItem = itemService.findOne(itemId);
        assertEquals(20, updatedItem.getStockQuantity());
    }
}
