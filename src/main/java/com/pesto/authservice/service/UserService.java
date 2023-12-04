package com.pesto.authservice.service;

import com.pesto.authservice.domain.entity.Order;
import com.pesto.authservice.domain.entity.OrderProduct;
import com.pesto.authservice.domain.entity.User;
import com.pesto.authservice.domain.repository.OrderRepository;
import com.pesto.authservice.domain.repository.UserRepository;
import com.pesto.authservice.service.messaging.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    public void processOrder(OrderMessage orderMessage) {
        Optional<User> optionalUser = userRepository.findById(orderMessage.getUserId());
        if (optionalUser.isEmpty())
            throw new IllegalStateException("User not found");
        User user = optionalUser.get();
        Order order = new Order();
        order.setExternalId(orderMessage.getOrderId());
        order.setUser(user);
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderMessage.OrderProduct messageOrderProduct: orderMessage.getOrderProducts()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setExternalId(messageOrderProduct.getProductId());
            orderProduct.setQuantity(messageOrderProduct.getQuantity());
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
    }
}
