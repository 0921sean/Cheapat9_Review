package powersell.cheapat9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import powersell.cheapat9.domain.OrderStatus;
import powersell.cheapat9.dto.order.OrderRequestDto;
import powersell.cheapat9.dto.order.OrderResponseDto;
import powersell.cheapat9.exception.NotEnoughStockException;
import powersell.cheapat9.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 매칭 위함

    /**
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderRequestDto requestDto) {
        Long orderId = orderService.saveOrder(requestDto);
        return ResponseEntity.ok(orderId);
    }

    /**
     * 주문 상태 변경
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        orderService.updateOrderStatus(id, orderStatus);
        return ResponseEntity.ok().build();
    }

    /**
     * 개별 주문 조회 (비밀번호 검증 포함)
     */
    @PostMapping("/detail")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByNumber(@RequestBody @Valid OrderRequestDto requestDto) {
        List<OrderResponseDto> orders = orderService.findAllOrdersByNumber(requestDto.getNumber())
                .stream()
                .filter(order -> passwordEncoder.matches(requestDto.getPw(), order.getPw()))
                .toList();
        return ResponseEntity.ok(orders);
    }

    /**
     * 전체 주문 조회 (관리자용)
     */
    @GetMapping("/admin")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    /**
     * 예외 처리 (재고 부족)
     */
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<String> handleNotEnoughStockException(NotEnoughStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}