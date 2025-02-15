package powersell.cheapat9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.domain.Order;
import powersell.cheapat9.domain.OrderStatus;
import powersell.cheapat9.dto.order.OrderRequestDto;
import powersell.cheapat9.dto.order.OrderResponseDto;
import powersell.cheapat9.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    /**
     * 주문 저장
     */
    @Transactional
    public Long saveOrder(OrderRequestDto requestDto) {
        Item item = itemService.getItem(requestDto.getItemId());

        Order order = Order.createOrder(
                item,
                requestDto.getCount(),
                requestDto.getName(),
                requestDto.getNumber(),
                requestDto.getZipcode(),
                requestDto.getAddress(),
                requestDto.getDongho(),
                requestDto.getPw()
        );

        orderRepository.save(order);
        itemService.updateItemStock(item.getId(), requestDto.getCount());

        return order.getId();
    }

    /**
     * 전체 주문 조회
     */
    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAllWithItems().stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 개별 주문 조회
     */
    public OrderResponseDto findOrder(Long id) {
        Order order = orderRepository.findByIdWithItem(id);
        return new OrderResponseDto(order);
    }

    /**
     * 전화번호로 주문 조회
     */
    public List<OrderResponseDto> findAllOrdersByNumber(String number) {
        return orderRepository.findAllByNumber(number).stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 주문 상태 변경
     */
    @Transactional
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findByIdWithItem(id);
        order.updateOrderStatus(orderStatus);
    }

    /**
     * 주문 정보 수정
     */
    @Transactional
    public void updateOrder(Long id, OrderRequestDto requestDto) {
        Order order = orderRepository.findByIdWithItem(id);

        order.updateOrder(
                requestDto.getCount(),
                requestDto.getName(),
                requestDto.getNumber(),
                requestDto.getZipcode(),
                requestDto.getAddress(),
                requestDto.getDongho(),
                requestDto.getPw()
        );
    }
}
