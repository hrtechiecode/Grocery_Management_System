package com.grocery.groceryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private long totalProducts;
    private long totalOrders;
    private long totalUsers;
    private double totalRevenue;
}