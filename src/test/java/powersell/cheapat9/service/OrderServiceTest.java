package powersell.cheapat9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.dto.item.ItemRequestDto;
import powersell.cheapat9.dto.order.OrderRequestDto;
import powersell.cheapat9.dto.order.OrderResponseDto;
import powersell.cheapat9.domain.OrderStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "admin.password=test1234")
@Transactional
public class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private ItemService itemService;

    /**
     * 1. 주문 저장 테스트
     */
    @Test
    public void saveOrderTest() {
        // given
        Long itemId = itemService.saveItem(createItemRequestDto());
        OrderRequestDto requestDto = createOrderRequest(itemId, "01012345678");

        // when
        Long orderId = orderService.saveOrder(requestDto);

        // then
        OrderResponseDto savedOrder = orderService.findOrder(orderId);
        assertNotNull(savedOrder);
        assertEquals(OrderStatus.WAITING, savedOrder.getStatus());
        assertEquals("01012345678", savedOrder.getNumber());
    }

    /**
     * 2. 주문 검색 테스트
     */
    @Test
    public void findAllOrdersTest() {
        // given
        Long itemId = itemService.saveItem(createItemRequestDto());

        orderService.saveOrder(createOrderRequest(itemId, "01098765432"));
        orderService.saveOrder(createOrderRequest(itemId, "01012345678"));

        // when
        List<OrderResponseDto> orders = orderService.findAllOrders();

        // then
        assertTrue(orders.size() >= 2);
    }

    @Test
    public void findOrdersByNumberTest() {
        // given
        Long itemId = itemService.saveItem(createItemRequestDto());

        orderService.saveOrder(createOrderRequest(itemId, "01077777777"));
        orderService.saveOrder(createOrderRequest(itemId, "01087654321"));

        // when
        List<OrderResponseDto> result = orderService.findAllOrdersByNumber("01077777777");

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
        Long itemId = itemService.saveItem(createItemRequestDto());
        Long orderId = orderService.saveOrder(createOrderRequest(itemId, "01088889999"));

        // when
        orderService.updateOrderStatus(orderId, OrderStatus.DELIVERING);

        // then
        OrderResponseDto updatedOrder = orderService.findOrder(orderId);
        assertEquals(OrderStatus.DELIVERING, updatedOrder.getStatus());
    }

    /**
     * 4. 주문 추가 및 재고 수정 테스트
     */
    @Test
    public void createOrderAndModifyStockTest() {
        // given
        Long itemId = itemService.saveItem(createItemRequestDto());
        OrderRequestDto orderRequest = createOrderRequest(itemId, "01099998888");

        // when
        Long orderId = orderService.saveOrder(orderRequest);

        // then
        OrderResponseDto savedOrder = orderService.findOrder(orderId);
        assertNotNull(savedOrder);
        assertEquals("01099998888", savedOrder.getNumber());
        assertEquals(OrderStatus.WAITING, savedOrder.getStatus());
        assertEquals(2, savedOrder.getCount());

        // 재고 감소 검증
        assertEquals(8, itemService.findItem(itemId).getStockQuantity()); // 원래 30개였으므로 10개 감소 확인
    }

    /**
     * 테스트에서 중복되는 ItemRequestDto 생성 메서드
     */
    private ItemRequestDto createItemRequestDto() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setName("Water");
        requestDto.setOriginalPrice(3000);
        requestDto.setPrice(600);
        requestDto.setStockQuantity(10);
        requestDto.setStartDate("2025-02-12 21:00:00");
        requestDto.setEndDate("2025-02-12 23:00:00");
        return requestDto;
    }

    /**
     * 테스트에서 중복되는 OrderRequestDto 생성 메서드
     */
    private OrderRequestDto createOrderRequest(Long itemId, String number) {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setItemId(itemId);
        requestDto.setCount(2);
        requestDto.setName("John Doe");
        requestDto.setNumber(number);
        requestDto.setZipcode("12345");
        requestDto.setAddress("Seoul, Korea");
        requestDto.setDongho("101-202");
        requestDto.setPw("1234");
        return requestDto;
    }
}
