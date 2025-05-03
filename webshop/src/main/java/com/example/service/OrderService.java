package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Phương thức tìm kiếm đơn hàng theo số điện thoại
    public List<Order> findOrdersByPhone(String phone) {
        return orderRepository.findByCustomerPhoneAndStatusNot(phone, "Hủy"); // Lọc bỏ status="Hủy"
    }

    // Phương thức thêm đơn hàng mới
    public void addOrder(Order order) {
        order.setStatus("PENDING"); // Đặt trạng thái mặc định
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Order order) {
        order.setStatus("Hủy");
        orderRepository.save(order); // Lưu trạng thái đã thay đổi
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Phương thức cập nhật đơn hàng
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}