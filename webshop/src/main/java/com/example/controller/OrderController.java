package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.model.Order;
import com.example.model.Product;
import com.example.service.ProductService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/order")
    public String handleOrder(
            @RequestParam("productId") Long productId, // Sửa từ @IRequestParam thành @RequestParam
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            Model model) {

        // Log dữ liệu nhận được
        System.out.println("Received order: productId=" + productId + ", name=" + name + ", phone=" + phone);

        // Lấy sản phẩm từ database
        Product product = productService.getProductById(productId);

        // Kiểm tra nếu sản phẩm không tồn tại
        if (product == null) {
            System.out.println("Product not found for ID: " + productId);
            model.addAttribute("error", "Sản phẩm không tồn tại");
            return "order_error";
        }

        // Tạo đơn hàng mới
        Order order = new Order(name, phone, product);

        // Lưu đơn hàng vào cơ sở dữ liệu
        try {
            orderService.addOrder(order);
            System.out.println("Order saved successfully: " + order.getId());
        } catch (Exception e) {
            System.err.println("Error saving order: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lưu đơn hàng: " + e.getMessage());
            return "order_error";
        }

        // Gửi dữ liệu qua view để hiển thị thông báo
        model.addAttribute("product", product);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);

        // Chuyển hướng đến trang xác nhận
        return "order_success";
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam(value = "orderId", required = false) String orderId, Model model) {
        System.out.println("Received cancel request with orderId: " + orderId);
        Long parsedOrderId;
        try {
            parsedOrderId = orderId != null ? Long.parseLong(orderId) : null;
        } catch (NumberFormatException e) {
            System.out.println("Invalid orderId format: " + orderId);
            model.addAttribute("error", "ID đơn hàng không hợp lệ.");
            return "order_error";
        }
        if (parsedOrderId == null) {
            System.out.println("OrderId is missing");
            model.addAttribute("error", "Thiếu ID đơn hàng.");
            return "order_error";
        }
        Order order = orderService.getOrderById(parsedOrderId);
        if (order != null) {
            System.out.println("Order found: " + order.getId());
            orderService.cancelOrder(order);
            model.addAttribute("message", "Đơn hàng đã được hủy thành công.");
            String phone = order.getCustomerPhone();
            if (phone == null || phone.isEmpty()) {
                System.out.println("Customer phone is null for orderId: " + parsedOrderId);
                model.addAttribute("error", "Không thể lấy số điện thoại.");
                return "order_error";
            }
            return "redirect:/cart/search?phone=" + phone;
        } else {
            System.out.println("Order not found for orderId: " + parsedOrderId);
            model.addAttribute("error", "Đơn hàng không tồn tại.");
            return "order_error";
        }
    }

    @GetMapping("/cart")
    public String viewCart() {
        return "cart"; // Trả về trang nhập số điện thoại
    }

    @GetMapping("/cart/search")
    public String searchOrders(@RequestParam String phone, Model model) {
        // Log dữ liệu tìm kiếm
        System.out.println("Searching orders for phone: " + phone);

        try {
            List<Order> orders = orderService.findOrdersByPhone(phone);
            model.addAttribute("orders", orders);
            model.addAttribute("phone", phone);
            return "order_list"; // Trả về trang danh sách đơn hàng
        } catch (Exception e) {
            System.err.println("Error searching orders: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tìm kiếm đơn hàng: " + e.getMessage());
            return "order_error";
        }
    }

    @PostMapping("/order/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("status") String status,
                                    Model model) {

        // Lấy đơn hàng theo ID
        Order order = orderService.getOrderById(orderId);

        // Kiểm tra nếu đơn hàng không tồn tại
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại");
            return "order_error"; // Trả về trang lỗi
        }

        // Cập nhật trạng thái đơn hàng
        order.setStatus(status);

        // Lưu đơn hàng đã cập nhật vào cơ sở dữ liệu
        orderService.updateOrder(order);

        // Chuyển hướng đến trang danh sách đơn hàng hoặc trang chi tiết đơn hàng
        model.addAttribute("order", order);
        return "order_success";  // Hoặc trang khác mà bạn muốn hiển thị sau khi cập nhật trạng thái
    }

}