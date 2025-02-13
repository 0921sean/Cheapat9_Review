package powersell.cheapat9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import powersell.cheapat9.domain.Item;
import powersell.cheapat9.domain.Order;
import powersell.cheapat9.domain.OrderStatus;
import powersell.cheapat9.form.OrderForm;
import powersell.cheapat9.service.ItemService;
import powersell.cheapat9.service.OrderService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping("/items/{itemId}/order")
    public String createForm(@PathVariable("itmeId") Long itemId, Model model) {

        model.addAttribute("form", new OrderForm());
        return "orders/createOrderForm";
    }

    @PostMapping("/items/{itemId}/order")
    public String create(@PathVariable("itemId") Long itemId, @ModelAttribute("form") OrderForm form) {

        Item item = itemService.findOne(itemId);

        Order order = new Order();
        order.setItem(item);
        order.setCount(form.getCount());
        order.setName(form.getName());
        order.setNumber(form.getNumber());
        order.setZipcode(form.getZipcode());
        order.setAddress(form.getAddress());
        order.setDongho(form.getDongho());
        order.setPw(form.getPw());
        order.setStatus(OrderStatus.WAITING);   // 기본이 '입금 전' 상태
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(item.getPrice() * order.getCount());
        itemService.updateStock(item, order.getCount());

        orderService.saveOrder(order);
        return "redirect:/";
    }
}
