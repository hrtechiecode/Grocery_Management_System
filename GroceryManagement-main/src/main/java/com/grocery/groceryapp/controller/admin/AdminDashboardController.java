package com.grocery.groceryapp.controller.admin;

import com.grocery.groceryapp.dto.DashboardResponse;
import com.grocery.groceryapp.repository.ItemRepository;
import com.grocery.groceryapp.repository.OrderRepository;
import com.grocery.groceryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public DashboardResponse getDashboardData() {

        return new DashboardResponse(
                itemRepository.count(),
                orderRepository.count(),
                userRepository.count(),
                orderRepository.getTotalRevenue() != null
                        ? orderRepository.getTotalRevenue()
                        : 0.0
        );
    }
}