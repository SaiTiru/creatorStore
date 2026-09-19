package com.stiru.creatorstore.services;

import com.stiru.creatorstore.entites.Order;
import com.stiru.creatorstore.entites.Orderitems;
import com.stiru.creatorstore.entites.Product;
import com.stiru.creatorstore.exceptions.BadRequestException;
import com.stiru.creatorstore.exceptions.ResourceNotFoundException;
import com.stiru.creatorstore.repositories.OrderRepository;
import com.stiru.creatorstore.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(Order order) {
        if (order == null || order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        Order newOrder = Order.builder()
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .status(order.getStatus() == null ? "PENDING" : order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
        newOrder.setOrderItems(new java.util.ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (Orderitems item : order.getOrderItems()) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                throw new BadRequestException("Each order item must include a valid product id");
            }

            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + item.getProduct().getId()));

            if (item.getQuantity() == null || item.getQuantity() < 1) {
                throw new BadRequestException("Quantity must be at least 1 for product id " + product.getId());
            }

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

            item.setProduct(product);
            item.setOrder(newOrder);
            item.setPriceAtPurchase(product.getPrice());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            newOrder.getOrderItems().add(item);
        }

        newOrder.setTotalPrice(total);
        return orderRepository.save(newOrder);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status is required");
        }

        order.setStatus(status.toUpperCase());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
