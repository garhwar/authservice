package com.pesto.authservice.service;

import com.pesto.authservice.domain.entity.Order;
import com.pesto.authservice.domain.entity.User;
import com.pesto.authservice.domain.entity.repository.OrderRepository;
import com.pesto.authservice.domain.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderAuthorizationService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public List<Order> fetchUserOrders(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());
        if (optionalUser.isEmpty())
            throw new IllegalStateException("Username not found");
        User user = optionalUser.get();
        return orderRepository.findByUserId(user.getId());
    }
}

