package com.OrderingSystem.OrderingSystem.Service;

import com.OrderingSystem.OrderingSystem.Dao.DishRepository;
import com.OrderingSystem.OrderingSystem.Dao.OrderRepository;
import com.OrderingSystem.OrderingSystem.Dto.OrderDetailsRequest;
import com.OrderingSystem.OrderingSystem.Dto.OrderResponse;
import com.OrderingSystem.OrderingSystem.Entity.OrderDetailEntity;
import com.OrderingSystem.OrderingSystem.Entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    public OrderResponse placeOrder(List<OrderDetailsRequest> orderItems, String paymentMethod) {
        OrderEntity order = new OrderEntity();
        order.setDateTime(LocalDateTime.now());
        order.setPayMethod(paymentMethod);

        OrderEntity orderDB = orderRepository.save(order);

        double totalPrice = 0;
        int index = 0;
        for (OrderDetailsRequest item : orderItems) {
            var dish = dishRepository.findById(item.getDishId()).orElse(null);
            if (dish == null) continue;

            double subTotal = dish.getPrice() * item.getQuantity();

            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.getId().setOrderId(orderDB.getId());
            orderDetail.getId().setDno(index);
            index += 1;
            orderDetail.setDish(dish);
            orderDetail.setCount(item.getQuantity());
            orderDetail.setTotal(subTotal);

            orderDB.addOrderDetail(orderDetail);

            totalPrice += subTotal;
        }

        orderDB.setTotalPrice(totalPrice);
        orderRepository.save(orderDB);

        return OrderResponse.builder()
                .orderId(orderDB.getId())
                .build();
    }


    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
