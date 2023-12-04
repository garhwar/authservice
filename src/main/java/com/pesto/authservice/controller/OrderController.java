package com.pesto.authservice.controller;

import com.pesto.authservice.domain.entity.Order;
import com.pesto.authservice.service.OrderAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    OrderAuthorizationService orderAuthorizationService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(Authentication authentication) {
        List<Order> userOrders = orderAuthorizationService.fetchUserOrders(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(userOrders);
    }
}
