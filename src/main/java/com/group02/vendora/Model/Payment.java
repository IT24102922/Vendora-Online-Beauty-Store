package com.group02.vendora.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "status")
    private String status;

    private String paymentMethod; // Online or COD
    private Double amount;

    private LocalDateTime paymentDate = LocalDateTime.now();

    private String transactionId;
}
