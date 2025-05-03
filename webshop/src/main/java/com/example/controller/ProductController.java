package com.example.controller;

import com.example.model.Product;
import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    public String home(@RequestParam(value = "search", required = false) String keyword, Model model) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(keyword); // Tìm kiếm sản phẩm
        } else {
            products = productRepository.findAll(); // Hiển thị tất cả sản phẩm nếu không tìm kiếm
        }
        model.addAttribute("products", products);
        return "index";
    }

    // Form đặt hàng
//    @PostMapping("/order")
//    public String placeOrder(@RequestParam String name, @RequestParam String phone, @RequestParam Long productId, Model model) {
//        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
//        Order order = new Order(name, phone, product);
//        orderRepository.save(order);  // Lưu đơn hàng vào cơ sở dữ liệu
//
//        model.addAttribute("order", order);
//        return "order_confirmation";  // Hiển thị trang xác nhận đơn hàng
//    }
}
