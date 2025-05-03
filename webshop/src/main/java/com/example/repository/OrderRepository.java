package com.example.repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Phương thức tìm đơn hàng theo số điện thoại của khách hàng
    List<Order> findByCustomerPhone(String phone);

    // Phương thức tìm đơn hàng theo số điện thoại và status không phải "Hủy"
    List<Order> findByCustomerPhoneAndStatusNot(String customerPhone, String status);
}