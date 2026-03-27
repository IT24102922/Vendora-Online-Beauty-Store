package com.group02.vendora.Controller;

import com.group02.vendora.Model.Order;
import com.group02.vendora.Model.Payment;
import com.group02.vendora.Repository.OrderRepository;
import com.group02.vendora.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order) {
        if (order.getStatus() == null) {
            order.setStatus("Pending");
        }
        if (order.getPaymentStatus() == null) {
            order.setPaymentStatus("PENDING");
        }
        return orderRepository.save(order);
    }


    @PutMapping("/{id}/cancel")
    @Transactional
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {

            if ("Shipped".equalsIgnoreCase(order.getStatus()) || "Delivered".equalsIgnoreCase(order.getStatus())) {
                return ResponseEntity.badRequest().body("Cannot cancel an order that is already shipped or delivered.");
            }

            order.setStatus("Cancelled");
            orderRepository.save(order);
            return ResponseEntity.ok().body("Order #" + id + " has been cancelled.");
        }).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return orderRepository.findById(id).map(order -> {

            String cleanStatus = status.replace("\"", "");
            order.setStatus(cleanStatus);
            return ResponseEntity.ok(orderRepository.save(order));
        }).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}/pay-confirm")
    @Transactional
    public ResponseEntity<?> confirmPayment(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            order.setPaymentStatus("PAID");
            orderRepository.save(order);


            List<Payment> payments = paymentRepository.findByOrderId(id);
            if (!payments.isEmpty()) {
                for (Payment payment : payments) {
                    payment.setStatus("PAID");
                    paymentRepository.save(payment);
                }
            }
            return ResponseEntity.ok(order);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
