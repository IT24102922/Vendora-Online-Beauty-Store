package com.group02.vendora.Controller;

import com.group02.vendora.Model.Payment;


import com.group02.vendora.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentRepository PaymentRepository;

    @PostMapping("/process")
    public Payment processPayment(@RequestBody Payment payment) {

        if (payment.getTransactionId() == null || payment.getTransactionId().isEmpty()) {
            payment.setTransactionId("TXN-" + System.currentTimeMillis());
        }
        return PaymentRepository.save(payment);
    }


    @GetMapping("/history")
    public List<Payment> getPaymentHistory() {
        return PaymentRepository.findAll();

    }
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return PaymentRepository.findAll();
    }
}
