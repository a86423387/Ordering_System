package com.OrderingSystem.OrderingSystem.Controller;

import com.OrderingSystem.OrderingSystem.Dto.DishDTO;
import com.OrderingSystem.OrderingSystem.Dto.OrderDetailsRequest;
import com.OrderingSystem.OrderingSystem.Dto.OrderResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.OrderingSystem.OrderingSystem.Entity.OrderEntity;
import com.OrderingSystem.OrderingSystem.Service.DishService;
import com.OrderingSystem.OrderingSystem.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class DishOrderController {
    private final DishService dishService;
    private final OrderService orderService;

    // 顯示新增訂單頁面
    @GetMapping("/create")
    public String createOrder(Model model) {
        List<DishDTO> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "create-order";
    }

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper(); // 確保 ObjectMapper 已初始化
        List<OrderDetailsRequest> orderItems = objectMapper.convertValue(
                request.get("orderItems"), new TypeReference<List<OrderDetailsRequest>>() {});
        String payMethod = (String) request.get("payMethod");
        return ResponseEntity.ok(orderService.placeOrder(orderItems, payMethod));
    }



    @GetMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable("orderId") Long orderId, Model model) {
        OrderEntity order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "order-details";
    }
}
