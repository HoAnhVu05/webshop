package com.example.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;


@Entity
@Table(name = "orders") // Đặt tên bảng là "orders"
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name") // Rõ ràng hóa tên cột
    private String customerName;

    @Column(name = "customer_phone") // Rõ ràng hóa tên cột
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "order_date")
    private LocalDate orderDate; // Thêm thuộc tính orderDate

    @Column(name = "status")
    private String status; // Thêm trường status để lưu trạng thái đơn hàng


    public Order() {
    }

    public Order(String customerName, String customerPhone, Product product) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.product = product;
        this.orderDate = LocalDate.now();
        this.status = "Đã đặt";
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}